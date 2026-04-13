package sis.lanes.conditions;

import sis.lanes.Lane;

import java.util.Set;

public record AndCondition(Set<Condition> conditions) implements Condition{

    public AndCondition(Condition... conditions){
        this(Set.of(conditions));
    }

    @Override
    public boolean isFulfilled(Lane lane) {
        return conditions
                .stream()
                .allMatch(condition -> condition.isFulfilled(lane));
    }
}
