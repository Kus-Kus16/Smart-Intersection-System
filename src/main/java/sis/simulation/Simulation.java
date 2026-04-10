package sis.simulation;

import sis.conditions.Condition;
import sis.intersection.Intersection;
import sis.lanes.Lane;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation {
    private final Intersection intersection;

    public Simulation(Intersection intersection) {
        this.intersection = intersection;
    }

    public void step() {
        ConditionedLanes conditionedLanes = groupLanes();
        SortedLanes sortedLanes = calculateChanges(conditionedLanes);
        queueLightChanges(sortedLanes.greenLanes(), sortedLanes.redLanes());
    }

    private ConditionedLanes groupLanes() {
        Set<Condition> conflictConditions = new HashSet<>();

        Map<Boolean, List<Lane>> lanesByReady = intersection.getAllLanes()
                .stream()
                .collect(Collectors.groupingBy(Lane::isReadyToChange));

        for (Lane lane : lanesByReady.getOrDefault(false, List.of())) {
            conflictConditions.addAll(lane.getConflictConditions());
        }

        List<Lane> changableLanes = lanesByReady.getOrDefault(true, List.of());
        Collections.shuffle(changableLanes); // TODO order by priority

        return new ConditionedLanes(conflictConditions, changableLanes);
    }

    private SortedLanes calculateChanges(ConditionedLanes conditionedLanes) {
        List<Lane> changableLanes = conditionedLanes.laneList();
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

        return new SortedLanes(greenLanes, redLanes);
    }

    private void queueLightChanges(List<Lane> greenLanes, List<Lane> redLanes) {
        for (Lane lane : greenLanes) {
            lane.queueGreenLight();
        }
        for (Lane lane : redLanes) {
            lane.queueRedLight();
        }
    }

}
