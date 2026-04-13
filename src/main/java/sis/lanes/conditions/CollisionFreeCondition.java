package sis.lanes.conditions;

import sis.lanes.Lane;
import sis.lanes.LaneType;

public record CollisionFreeCondition() implements Condition {
    @Override
    public boolean isFulfilled(Lane lane) {
        return lane.isLaneType(LaneType.COLLISION_FREE);
    }
}
