package sis.lanes;


import sis.intersection.Intersection;
import sis.lanes.conditions.*;
import sis.lights.SimpleTrafficLight;

import java.util.List;
import java.util.Set;

public class LeftLane extends Lane {

    public LeftLane(Direction entry, Intersection intersection) {
        List<Direction> exits = List.of(Direction.leftFrom(entry));
        Set<LaneType> laneTypes = Set.of(LaneType.CAR, LaneType.COLLISION_FREE);
        super(laneTypes, entry, exits, intersection);

        this.trafficLight = new SimpleTrafficLight();
    }


    @Override
    protected List<Condition> generateConflictConditions() {
        Direction entry = this.entry;
        Direction left = Direction.leftFrom(entry);
        Direction right = Direction.rightFrom(entry);
        Direction straight = Direction.straightFrom(entry);

        return List.of(
                new CarExitCondition(left),
                new AndCondition(new CarEntryCondition(right), new CarExitCondition(entry)),
                new AndCondition(new CarEntryCondition(straight), new CarExitCondition(entry)),
                new AndCondition(new CarEntryCondition(left), new CarExitCondition(straight)),
                new AndCondition(new CarEntryCondition(left), new CarExitCondition(right)),
                new PedestriansCondition(entry),
                new PedestriansCondition(left)
        );
    }

    @Override
    protected String getRepresentation() {
        return "(!<)";
    }
}
