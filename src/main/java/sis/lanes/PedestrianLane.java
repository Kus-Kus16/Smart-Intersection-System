package sis.lanes;

import sis.conditions.*;
import sis.intersection.Intersection;
import sis.lights.PedestrianTrafficLight;

import java.util.List;

public class PedestrianLane extends Lane {

    public PedestrianLane(Direction side, Intersection intersection) {
        List<Direction> exits = List.of(side);
        super(LaneType.PEDESTRIAN, side, exits, intersection);

        this.trafficLight = new PedestrianTrafficLight();
    }

    @Override
    protected List<Condition> generateConflictConditions() {
        Direction side = this.entry;

        return List.of(
                new CarEntryCondition(side),
                new AndCondition(new CarEntryCondition(Direction.straightFrom(side)), new CarExitCondition(side)),
                new AndCondition(new CarExitCondition(side), new CarCollisionFreeCondition())
        );

    }

    @Override
    protected String getRepresentation() {
        return "(A)";
    }
}
