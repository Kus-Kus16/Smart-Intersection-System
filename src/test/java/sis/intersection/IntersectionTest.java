package sis.intersection;

import org.junit.jupiter.api.Test;
import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;
import sis.lanes.TestLane;
import sis.users.RoadUser;
import sis.users.TestRoadUser;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class IntersectionTest {
    @Test
    public void addUserTest() {
        Intersection intersection = new Intersection();
        Lane lane = new TestLane(
                Set.of(LaneType.CAR),
                Direction.NORTH,
                Set.of(Direction.SOUTH, Direction.EAST)
        );
        RoadUser user = new TestRoadUser(
                Direction.NORTH,
                Direction.SOUTH,
                LaneType.CAR
        );

        intersection.addLane(lane);

        assertDoesNotThrow(() -> intersection.addUser(user));
        assertEquals(1, lane.getQueueSize());
        assertEquals(lane, user.getLane());
    }

    @Test
    public void addWrongUserThrows() {
        Intersection intersection = new Intersection();
        Lane lane = new TestLane(
                Set.of(LaneType.CAR),
                Direction.NORTH,
                Set.of(Direction.SOUTH, Direction.EAST)
        );
        RoadUser user1 = new TestRoadUser(
                Direction.NORTH,
                Direction.SOUTH,
                LaneType.PEDESTRIAN
        );
        RoadUser user2 = new TestRoadUser(
                Direction.NORTH,
                Direction.WEST,
                LaneType.CAR
        );

        intersection.addLane(lane);

        assertThrows(IllegalStateException.class, () -> intersection.addUser(user1));
        assertThrows(IllegalStateException.class, () -> intersection.addUser(user2));
        assertEquals(0, lane.getQueueSize());
        assertNull(user1.getLane());
        assertNull(user2.getLane());
    }
}
