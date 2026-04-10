package sis.lanes;

import sis.Direction;
import sis.conditions.Condition;
import sis.conditions.CarEntryCondition;
import sis.conditions.CarExitCondition;
import sis.conditions.PedestriansCondition;
import sis.intersection.Intersection;
import sis.lights.GreenArrowTrafficLight;

import java.util.List;

public class StraightRightLane extends Lane{

    public StraightRightLane(Direction entry, Intersection intersection) {
        List<Direction> exits = List.of(Direction.straightFrom(entry), Direction.rightFrom(entry));
        super(LaneType.CAR, entry, exits, intersection);

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
}
