package sis.users;

import sis.intersection.Intersection;
import sis.intersection.IntersectionSide;
import sis.lights.TrafficLightState;

public class Pedestrian extends RoadUser{

    @Override
    public boolean canMove(TrafficLightState lightState, Intersection intersection) {
        return switch (lightState.getEntryType()) {
            case CARELESS -> true;
            case FORBIDDEN -> false;
            case GIVE_WAY -> throw new IllegalStateException("Pedestrians cannot give way");
        };
    }

    @Override
    public void exit(Intersection intersection) {
        IntersectionSide side = intersection.getIntersectionSide(this.exitDirection);
        side.occupyExit();
        side.occupyPedestrian();
    }
}
