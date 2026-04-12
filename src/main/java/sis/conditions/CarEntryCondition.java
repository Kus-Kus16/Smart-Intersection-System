package sis.conditions;

import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;

public class CarEntryCondition implements Condition {
    private final Direction direction;

    public CarEntryCondition(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean isFulfilled(Lane lane) {
        if (lane.isLaneType(LaneType.PEDESTRIAN)) {
            return false;
        }

        return lane.getEntry().equals(direction);
    }
}
