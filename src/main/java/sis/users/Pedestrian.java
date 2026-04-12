package sis.users;

import sis.intersection.Intersection;
import sis.intersection.IntersectionSide;
import sis.lanes.Direction;
import sis.lanes.LaneType;
import sis.lights.TrafficLightState;

public class Pedestrian extends RoadUser{

    public Pedestrian(Direction entryDirection, Direction exitDirection, String id) {
        super(entryDirection, exitDirection, id);
    }

    @Override
    public boolean canMove(TrafficLightState lightState, Intersection intersection) {
        return switch (lightState.getEntryType()) {
            case CARELESS -> true;
            case FORBIDDEN -> false;
            case GIVE_WAY -> throw new IllegalStateException("Pedestrians cannot give way");
        };
    }

    @Override
    public LaneType getExpectedLaneType() {
        return LaneType.PEDESTRIAN;
    }

}
