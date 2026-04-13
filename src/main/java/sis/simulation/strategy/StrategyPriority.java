package sis.simulation.strategy;

import sis.conditions.Condition;
import sis.lanes.Lane;
import sis.lights.TrafficLight;
import sis.simulation.ConditionedLanes;
import sis.simulation.ActionGroupedLanes;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class StrategyPriority extends SimulationStrategy {
    private final Function<Integer, Double> positiveFunction = (v) -> {
        return 0.3 * Math.pow(v, 2);
    };
    private final Function<Integer, Double> negativeFunction = (v) -> {
        return 12.0 / (v + 1);
    };
    private final Function<Integer, Double> trafficFunction = Double::valueOf;

    @Override
    protected ConditionedLanes orderLanes(ConditionedLanes conditionedLanes) {
        conditionedLanes.changableLanes().sort(Comparator
                .comparingDouble(this::getLanePriority)
                .thenComparingInt(Lane::getQueueSize)
                .reversed()
        );
        return conditionedLanes;
    }

    public double getLanePriority(Lane lane) {//todo change to private
        if (lane.getQueueSize() == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        return getTimePriority(lane) * getTrafficPriority(lane);
    }

    private double getTimePriority(Lane lane) {
        TrafficLight trafficLight = lane.getTrafficLight();
        int waitingTime = trafficLight.getWaitingTime();

        Function<Integer, Double> priorityFunction = negativeFunction;
        if (!trafficLight.isCurrentlyGreen()) {
            priorityFunction = positiveFunction;
        }

        System.out.println("x: " + waitingTime + "y: " + priorityFunction.apply(waitingTime));
        return priorityFunction.apply(waitingTime);
    }

    private double getTrafficPriority(Lane lane) {
        int traffic = lane.getQueueSize();
        return trafficFunction.apply(traffic);
    }

    @Override
    public ActionGroupedLanes groupLanes(ConditionedLanes conditionedLanes) {
        List<Lane> orderedLanes = conditionedLanes.changableLanes();
        Set<Condition> conflictConditions = conditionedLanes.conditionSet();

        List<Lane> greenLanes = new LinkedList<>();
        List<Lane> redLanes = new LinkedList<>();

        if (!orderedLanes.isEmpty()) {
            Lane important = orderedLanes.removeFirst();

            if (canAddToGreen(important, conflictConditions)) {
                greenLanes.add(important);
            } else {
                redLanes.add(important);
            }
            conflictConditions.addAll(important.getConflictConditions());
        }

        this.simplyGroupLanes(orderedLanes, conflictConditions, greenLanes, redLanes);

        return new ActionGroupedLanes(greenLanes, redLanes, conditionedLanes.nonChangableLanes());
    }

    @Override
    protected boolean canAddToGreen(Lane lane, Set<Condition> conflictConditions) {
        if (lane.getQueueSize() == 0) {
            return false;
        }

        return super.canAddToGreen(lane, conflictConditions);
    }

}
