package sis.users;

import sis.intersection.Intersection;
import sis.lanes.Direction;
import sis.lanes.LaneType;
import sis.lights.TrafficLightState;

public class TestRoadUser extends RoadUser {
    private final LaneType expectedLaneType;

    public TestRoadUser(Direction entry, Direction exit, LaneType expectedLaneType) {
        super(entry, exit, "");
        this.expectedLaneType = expectedLaneType;
    }

    public TestRoadUser(Direction entry, Direction exit) {
        this(entry, exit, null);
    }

    @Override
    public boolean canMove(TrafficLightState lightState, Intersection intersection) {
        return lightState.equals(TrafficLightState.GREEN);
    }

    @Override
    public LaneType getExpectedLaneType() {
        return expectedLaneType;
    }

    @Override
    public void exit(Intersection intersection) {
    }
}
