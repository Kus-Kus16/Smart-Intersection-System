package sis.users;

import sis.intersection.Intersection;
import sis.intersection.IntersectionSide;
import sis.lanes.LaneType;
import sis.lights.TrafficLightState;

public class Car extends RoadUser{

    @Override
    public boolean canMove(TrafficLightState lightState, Intersection intersection) {
        return switch (lightState.getEntryType()) {
            case CARELESS -> true;
            case FORBIDDEN -> false;
            case GIVE_WAY -> {
                if (intersection.hasPedestrianTraffic(this.entryDirection)
                        || intersection.hasExitTraffic(this.exitDirection)) {
                    yield false;
                }

                yield true;
            }
        };
    }

    @Override
    public void exit(Intersection intersection) {
        IntersectionSide side = intersection.getIntersectionSide(this.exitDirection);
        side.occupyExit();
    }
}
