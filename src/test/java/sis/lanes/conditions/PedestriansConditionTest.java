package sis.lanes.conditions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;
import sis.lanes.TestLane;
import sis.lanes.conditions.Condition;
import sis.lanes.conditions.PedestriansCondition;

import java.util.Set;

public class PedestriansConditionTest {

    @Test
    public void isFulfilledTest() {
        Lane carLane = new TestLane(
                Set.of(LaneType.CAR),
                Direction.SOUTH,
                Set.of(Direction.NORTH));
        Lane pedestrianLane1 = new TestLane(
                Set.of(LaneType.PEDESTRIAN),
                Direction.NORTH,
                Set.of(Direction.NORTH)
        );
        Lane pedestrianLane2 = new TestLane(
                Set.of(LaneType.PEDESTRIAN),
                Direction.SOUTH,
                Set.of(Direction.SOUTH)
        );
        Condition condition = new PedestriansCondition(Direction.SOUTH);


        assertFalse(condition.isFulfilled(carLane));
        assertFalse(condition.isFulfilled(pedestrianLane1));
        assertTrue(condition.isFulfilled(pedestrianLane2));
    }
}
