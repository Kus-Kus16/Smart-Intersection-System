package sis.conditions;

import sis.lanes.Lane;

import java.util.List;

public class AndCondition implements Condition{
    List<Condition> conditions;

    public AndCondition(Condition... conditions){
        this.conditions = List.of(conditions);
    }

    @Override
    public boolean isFulfilled(Lane lane) {
        return conditions
                .stream()
                .allMatch(condition -> condition.isFulfilled(lane));
    }
}
