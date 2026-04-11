package sis.users;

import sis.lanes.Direction;
import sis.intersection.Intersection;
import sis.lights.TrafficLightState;

public abstract class RoadUser {
    protected Direction entryDirection;
    protected Direction exitDirection;


    public abstract boolean canMove(TrafficLightState lightState, Intersection intersection);
    public abstract void exit(Intersection intersection);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " Entry: " + this.entryDirection + " Exit: " + this.exitDirection;
    }
}
