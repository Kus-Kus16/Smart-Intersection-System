package sis.lanes.conditions;

import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;

public record CarExitCondition(Direction direction) implements Condition {

    @Override
    public boolean isFulfilled(Lane lane) {
        if (lane.isLaneType(LaneType.PEDESTRIAN)) {
            return false;
        }

        return lane.getExits().contains(direction);
    }
}
