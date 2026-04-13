package sis.lanes.conditions;

import sis.lanes.Lane;

@FunctionalInterface
public interface Condition {
    boolean isFulfilled(Lane lane);

}
