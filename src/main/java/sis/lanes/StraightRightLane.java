package sis.lanes;

import sis.lanes.conditions.Condition;
import sis.lanes.conditions.CarEntryCondition;
import sis.lanes.conditions.CarExitCondition;
import sis.lanes.conditions.PedestriansCondition;
import sis.intersection.Intersection;
import sis.lights.GreenArrowTrafficLight;

import java.util.List;
import java.util.Set;

public class StraightRightLane extends Lane{

    public StraightRightLane(Direction entry, Intersection intersection) {
        List<Direction> exits = List.of(Direction.straightFrom(entry), Direction.rightFrom(entry));
        Set<LaneType> laneTypes = Set.of(LaneType.CAR);
        super(laneTypes, entry, exits, intersection);

        this.trafficLight = new GreenArrowTrafficLight();
    }

    @Override
    protected List<Condition> generateConflictConditions() {
        Direction entry = this.entry;
        Direction straight = Direction.straightFrom(entry);
        Direction right = Direction.rightFrom(entry);

        return List.of(
                new PedestriansCondition(entry),
                new PedestriansCondition(straight),
                new CarExitCondition(right),
                new CarExitCondition(straight),
                new CarEntryCondition(right)
        );
    }

    @Override
    protected String getRepresentation() {
        return "(^>)";
    }
}
