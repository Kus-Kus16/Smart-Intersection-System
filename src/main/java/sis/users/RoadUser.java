package sis.users;

import sis.intersection.IntersectionSide;
import sis.lanes.Direction;
import sis.intersection.Intersection;
import sis.lanes.Lane;
import sis.lanes.LaneType;
import sis.lights.TrafficLightState;

public abstract class RoadUser {
    protected Lane lane;
    protected Direction entryDirection;
    protected Direction exitDirection;
    protected String id;

    public RoadUser(Direction entryDirection, Direction exitDirection, String id) {
        this.entryDirection = entryDirection;
        this.exitDirection = exitDirection;
        this.id = id;
    }

    public abstract boolean canMove(TrafficLightState lightState, Intersection intersection);

    public abstract LaneType getExpectedLaneType();

    public void exit(Intersection intersection) {
        IntersectionSide side = intersection.getIntersectionSide(exitDirection);
        side.occupyExit(this.lane.getLaneTypes());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " Entry: " + this.entryDirection + " Exit: " + this.exitDirection;
    }

    public Lane getLane() {
        return lane;
    }

    public Direction getEntryDirection() {
        return entryDirection;
    }

    public Direction getExitDirection() {
        return exitDirection;
    }

    public String getId() {
        return id;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }
}
