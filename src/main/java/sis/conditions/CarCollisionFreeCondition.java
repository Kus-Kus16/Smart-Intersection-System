package sis.conditions;

import sis.lanes.Lane;
import sis.lanes.LaneType;

public class CarCollisionFreeCondition implements Condition {
    @Override
    public boolean isFulfilled(Lane lane) {
        return lane.getLaneType().equals(LaneType.CAR_COLLISION_FREE);
    }
}
