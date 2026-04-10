package sis.simulation;

import sis.conditions.Condition;
import sis.lanes.Lane;

import java.util.List;
import java.util.Set;

public record ConditionedLanes(
        Set<Condition> conditionSet,
        List<Lane> laneList
) {
}
