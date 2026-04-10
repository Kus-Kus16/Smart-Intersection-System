package sis.conditions;

import sis.lanes.Lane;

@FunctionalInterface
public interface Condition {
    boolean isFulfilled(Lane lane);

    default Condition not() {
        return (lane) -> !isFulfilled(lane);
    }

}
