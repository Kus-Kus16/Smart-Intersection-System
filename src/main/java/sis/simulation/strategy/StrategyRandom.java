package sis.simulation.strategy;

import sis.conditions.Condition;
import sis.lanes.Lane;
import sis.simulation.ConditionedLanes;
import sis.simulation.ActionGroupedLanes;

import java.util.*;

public class StrategyRandom extends SimulationStrategy {
    private final Random random;

    public StrategyRandom(long seed) {
        this.random = new Random(seed);
    }

    @Override
    protected ConditionedLanes orderLanes(ConditionedLanes conditionedLanes) {
        Collections.shuffle(conditionedLanes.changableLanes(), random);
        return conditionedLanes;
    }

    @Override
    public ActionGroupedLanes groupLanes(ConditionedLanes conditionedLanes) {
        List<Lane> orderedLanes = conditionedLanes.changableLanes();
        Set<Condition> conflictConditions = conditionedLanes.conditionSet();

        List<Lane> greenLanes = new LinkedList<>();
        List<Lane> redLanes = new LinkedList<>();
        this.simplyGroupLanes(orderedLanes, conflictConditions, greenLanes, redLanes);

        return new ActionGroupedLanes(greenLanes, redLanes, conditionedLanes.nonChangableLanes());
    }
}
