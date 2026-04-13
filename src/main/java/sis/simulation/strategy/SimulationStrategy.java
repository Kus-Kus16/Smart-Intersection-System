package sis.simulation.strategy;

import sis.conditions.Condition;
import sis.lanes.Lane;
import sis.simulation.ConditionedLanes;
import sis.simulation.ActionGroupedLanes;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SimulationStrategy {

    public ActionGroupedLanes groupLanes(List<Lane> lanes) {
        ConditionedLanes conditionedLanes = this.conditionLanes(lanes);
        conditionedLanes = this.orderLanes(conditionedLanes);
        return this.groupLanes(conditionedLanes);
    }

    protected ConditionedLanes conditionLanes(List<Lane> lanes) {
        Set<Condition> conflictConditions = new HashSet<>();
        Map<Boolean, List<Lane>> lanesByReady = lanes
                .stream()
                .collect(Collectors.groupingBy(Lane::isReadyToChange));

        List<Lane> nonChangableLanes = lanesByReady.getOrDefault(false, Collections.emptyList());
        for (Lane lane : nonChangableLanes) {
            conflictConditions.addAll(lane.getCurrentConflictConditions());
        }

        List<Lane> changableLanes = lanesByReady.getOrDefault(true,Collections.emptyList());

        return new ConditionedLanes(conflictConditions, changableLanes, nonChangableLanes);
    }

    protected abstract ConditionedLanes orderLanes(ConditionedLanes conditionedLanes);
    protected abstract ActionGroupedLanes groupLanes(ConditionedLanes conditionedLanes);

    protected void simplyGroupLanes(List<Lane> lanes, Set<Condition> conflictConditions,
                                    List<Lane> greenLanes, List<Lane> redLanes) {
        for (Lane lane : lanes) {
            if (canAddToGreen(lane, conflictConditions)) {
                greenLanes.add(lane);
                conflictConditions.addAll(lane.getConflictConditions());
            } else {
                redLanes.add(lane);
            }
        }
    }

    protected boolean canAddToGreen(Lane lane, Set<Condition> conflictConditions) {
        boolean passes = true;
        for (Condition conflict : conflictConditions) {
            if (conflict.isFulfilled(lane)) {
                passes = false;
                break;
            }
        }

        return passes;
    }

}
