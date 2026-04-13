package sis.lanes.conditions;

import org.junit.jupiter.api.Test;
import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;
import sis.lanes.TestLane;
import sis.lanes.conditions.CarEntryCondition;
import sis.lanes.conditions.Condition;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarEntryConditionTest {

    @Test
    public void isFulfilledTest() {
        Lane carLane1 = new TestLane(
                Set.of(LaneType.CAR),
                Direction.SOUTH,
                Set.of(Direction.NORTH)
        );
        Lane carLane2 = new TestLane(
                Set.of(LaneType.CAR),
                Direction.NORTH,
                Set.of(Direction.SOUTH)
        );
        Lane carLane3 = new TestLane(
                Set.of(LaneType.CAR),
                Direction.SOUTH,
                Set.of(Direction.SOUTH)
        );
        Lane pedestrianLane = new TestLane(
                Set.of(LaneType.PEDESTRIAN),
                Direction.SOUTH,
                Set.of(Direction.SOUTH)
        );
        Condition condition = new CarEntryCondition(Direction.SOUTH);


        assertFalse(condition.isFulfilled(carLane2));
        assertFalse(condition.isFulfilled(pedestrianLane));

        assertTrue(condition.isFulfilled(carLane1));
        assertTrue(condition.isFulfilled(carLane3));
    }
}
