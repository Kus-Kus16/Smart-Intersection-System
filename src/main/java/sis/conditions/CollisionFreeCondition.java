package sis.conditions;

import sis.lanes.Lane;
import sis.lanes.LaneType;

public class CollisionFreeCondition implements Condition {
    @Override
    public boolean isFulfilled(Lane lane) {
        return lane.isLaneType(LaneType.COLLISION_FREE);
    }
}
