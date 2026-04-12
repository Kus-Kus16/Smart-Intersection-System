package sis.conditions;

import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;

public class PedestriansCondition implements Condition {
    private final Direction direction;

    public PedestriansCondition(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean isFulfilled(Lane lane) {
        return lane.isLaneType(LaneType.PEDESTRIAN) && lane.getEntry().equals(direction);
    }
}
