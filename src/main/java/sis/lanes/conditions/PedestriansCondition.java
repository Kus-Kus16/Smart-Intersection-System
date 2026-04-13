package sis.lanes.conditions;

import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;

public record PedestriansCondition(Direction direction) implements Condition {

    @Override
    public boolean isFulfilled(Lane lane) {
        return lane.isLaneType(LaneType.PEDESTRIAN) && lane.getEntry().equals(direction);
    }
}
