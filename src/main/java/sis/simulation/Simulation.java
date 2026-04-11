package sis.simulation;

import sis.conditions.Condition;
import sis.intersection.Intersection;
import sis.lanes.Lane;
import sis.visualizatoon.Visualizer;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation {
    private final Intersection intersection;
    private final Visualizer visualizer;

    public Simulation(Intersection intersection, Visualizer visualizer) {
        this.intersection = intersection;
        this.visualizer = visualizer;
    }

    public void step() {
        intersection.reset();

        ConditionedLanes conditionedLanes = groupLanes();
        SortedLanes sortedLanes = calculateChanges(conditionedLanes);
        queueLightChanges(sortedLanes);

        moveLanes(sortedLanes);

        visualizer.visualize(intersection);
    }

    private ConditionedLanes groupLanes() {
        Set<Condition> conflictConditions = new HashSet<>();

        Map<Boolean, List<Lane>> lanesByReady = intersection.getAllLanes()
                .stream()
                .collect(Collectors.groupingBy(Lane::isReadyToChange));

        List<Lane> nonChangableLanes = lanesByReady.getOrDefault(false, Collections.emptyList());
        for (Lane lane : nonChangableLanes) {
            conflictConditions.addAll(lane.getConflictConditions());
        }

        List<Lane> changableLanes = lanesByReady.getOrDefault(true, List.of());
        Collections.shuffle(changableLanes); // TODO order by priority

        return new ConditionedLanes(conflictConditions, changableLanes, nonChangableLanes);
    }

    private SortedLanes calculateChanges(ConditionedLanes conditionedLanes) {
        List<Lane> changableLanes = conditionedLanes.changableLanes();
        Set<Condition> conflictConditions = conditionedLanes.conditionSet();

        List<Lane> greenLanes = new LinkedList<>();
        List<Lane> redLanes = new LinkedList<>();

        for (Lane lane : changableLanes) {
            boolean passed = true;

            for (Condition conflict : conflictConditions) {
                if (conflict.isFulfilled(lane)) {
                    passed = false;
                    break;
                }
            }

            if (passed) {
                greenLanes.add(lane);
                conflictConditions.addAll(lane.getConflictConditions());
            } else {
                redLanes.add(lane);
            }
        }

        return new SortedLanes(greenLanes, redLanes, conditionedLanes.nonChangableLanes());
    }

    private void queueLightChanges(SortedLanes lanes) {
        for (Lane lane : lanes.greenLanes()) {
            lane.queueGreenLight();
        }
        for (Lane lane : lanes.redLanes()) {
            lane.queueRedLight();
        }
        for (Lane lane : lanes.nonChangableLanes()) {
            lane.doNotChange();
        }
    }

    private void moveLanes(SortedLanes lanes) {
        for (Lane lane : lanes.nonChangableLanes()) {
            lane.makeStep();
        }
        for (Lane lane : lanes.greenLanes()) {
            lane.makeStep();
        }
        for (Lane lane : lanes.redLanes()) {
            lane.makeStep();
        }
    }

}
