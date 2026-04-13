package sis.lanes.conditions;

import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;

public record CarEntryCondition(Direction direction) implements Condition {

    @Override
    public boolean isFulfilled(Lane lane) {
        if (lane.isLaneType(LaneType.PEDESTRIAN)) {
            return false;
        }

        return lane.getEntry().equals(direction);
    }
}
