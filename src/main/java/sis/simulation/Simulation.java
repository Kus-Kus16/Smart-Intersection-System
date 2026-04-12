package sis.simulation;

import sis.commands.Command;
import sis.conditions.Condition;
import sis.intersection.Intersection;
import sis.lanes.Lane;
import sis.util.CommandReader;
import sis.visualization.Visualizer;

import java.io.EOFException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Simulation {
    private final Intersection intersection;
    private final Visualizer visualizer;
    private final CommandReader commandReader;

    public Simulation(Intersection intersection, Visualizer visualizer, CommandReader commandReader) {
        this.intersection = intersection;
        this.visualizer = visualizer;
        this.commandReader = commandReader;
    }

    public void run() throws IOException {
        commandReader.open();
        while (true) {
            try {
                Command command = commandReader.readNextCommand();
                command.execute(this);
            } catch (EOFException e) {
                commandReader.close();
                return;
            }
        }
    }

    public void step() {
        intersection.reset();

        ConditionedLanes conditionedLanes = groupLanes();
        GroupedLanes groupedLanes = calculateChanges(conditionedLanes);
        queueLightChanges(groupedLanes);
        moveLanes(groupedLanes);

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

    private GroupedLanes calculateChanges(ConditionedLanes conditionedLanes) {
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

        return new GroupedLanes(greenLanes, redLanes, conditionedLanes.nonChangableLanes());
    }

    private void queueLightChanges(GroupedLanes lanes) {
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

    private void moveLanes(GroupedLanes lanes) {
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

    public Intersection getIntersection() {
        return intersection;
    }
}
