package sis.conditions;

import sis.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;

public class CarExitCondition implements Condition {
    private final Direction direction;

    public CarExitCondition(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean isFulfilled(Lane lane) {
        if (lane.getLaneType().equals(LaneType.PEDESTRIAN)) {
            return false;
        }

        return lane.getExits().contains(direction);
    }
}
