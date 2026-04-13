package sis.lanes.conditions;

import org.junit.jupiter.api.Test;
import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;
import sis.lanes.TestLane;
import sis.lanes.conditions.CollisionFreeCondition;
import sis.lanes.conditions.Condition;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionFreeConditionTest {

    @Test
    public void isFulfilledTest() {
        Lane carLane1 = new TestLane(
                Set.of(LaneType.CAR),
                Direction.SOUTH,
                Set.of(Direction.NORTH));
        Lane freeLane = new TestLane(
                Set.of(LaneType.COLLISION_FREE),
                Direction.SOUTH,
                Set.of(Direction.NORTH));
        Lane carLane2 = new TestLane(
                Set.of(LaneType.CAR, LaneType.COLLISION_FREE),
                Direction.SOUTH,
                Set.of(Direction.NORTH));
        Lane pedestrianLane1 = new TestLane(
                Set.of(LaneType.PEDESTRIAN),
                Direction.NORTH,
                Set.of(Direction.NORTH)
        );
        Lane pedestrianLane2 = new TestLane(
                Set.of(LaneType.PEDESTRIAN, LaneType.COLLISION_FREE),
                Direction.NORTH,
                Set.of(Direction.NORTH)
        );
        Condition condition = new CollisionFreeCondition();

        assertFalse(condition.isFulfilled(carLane1));
        assertFalse(condition.isFulfilled(pedestrianLane1));

        assertTrue(condition.isFulfilled(freeLane));
        assertTrue(condition.isFulfilled(carLane2));
        assertTrue(condition.isFulfilled(pedestrianLane2));
    }
}
