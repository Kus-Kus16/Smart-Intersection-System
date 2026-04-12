package sis.users;

import sis.intersection.Intersection;
import sis.intersection.IntersectionSide;
import sis.lanes.Direction;
import sis.lanes.LaneType;
import sis.lights.TrafficLightState;

public class Car extends RoadUser{

    public Car(Direction entryDirection, Direction exitDirection, String id) {
        super(entryDirection, exitDirection, id);
    }

    @Override
    public boolean canMove(TrafficLightState lightState, Intersection intersection) {
        return switch (lightState.getEntryType()) {
            case CARELESS -> true;
            case FORBIDDEN -> false;
            case GIVE_WAY -> {
                if (intersection.hasExitTraffic(this.entryDirection, LaneType.PEDESTRIAN)
                    || intersection.hasExitTraffic(this.exitDirection)) {
                    yield false;
                }

                yield true;
            }
        };
    }

    @Override
    public LaneType getExpectedLaneType() {
        return LaneType.CAR;
    }

}
