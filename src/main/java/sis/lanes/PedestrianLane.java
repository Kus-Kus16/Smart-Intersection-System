package sis.lanes;

import sis.intersection.Intersection;
import sis.lanes.conditions.*;
import sis.lights.PedestrianTrafficLight;

import java.util.List;
import java.util.Set;

public class PedestrianLane extends Lane {

    public PedestrianLane(Direction side, Intersection intersection) {
        super(Set.of(LaneType.PEDESTRIAN), side, List.of(side), intersection);
        this.trafficLight = new PedestrianTrafficLight();
    }

    @Override
    protected List<Condition> generateConflictConditions() {
        Direction side = this.entry;

        return List.of(
                new CarEntryCondition(side),
                new AndCondition(new CarEntryCondition(Direction.straightFrom(side)), new CarExitCondition(side)),
                new AndCondition(new CarExitCondition(side), new CollisionFreeCondition())
        );

    }

    @Override
    protected String getRepresentation() {
        return "(A)";
    }
}
