package sis.lanes;

import org.junit.jupiter.api.Test;
import sis.lights.TestTrafficLight;
import sis.lights.TrafficLight;
import sis.lights.TrafficLightState;
import sis.users.RoadUser;
import sis.users.TestRoadUser;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LaneTest {

    @Test
    public void laneInitTest() {
        TrafficLight trafficLight = new TestTrafficLight(TrafficLightState.RED);
        Lane lane = new TestLane(
                Set.of(LaneType.COLLISION_FREE, LaneType.CAR),
                Direction.EAST,
                Set.of(Direction.NORTH, Direction.SOUTH),
                trafficLight
        );

        assertTrue(lane.isLaneType(LaneType.CAR));
        assertTrue(lane.isLaneType(LaneType.COLLISION_FREE));
        assertFalse(lane.isLaneType(LaneType.PEDESTRIAN));

        assertTrue(lane.hasExitOn(Direction.NORTH));
        assertTrue(lane.hasExitOn(Direction.SOUTH));
        assertFalse(lane.hasExitOn(Direction.EAST));
    }

    @Test
    public void addRoadUserTest() {
        Lane lane = new TestLane(
                Set.of(LaneType.CAR),
                Direction.SOUTH,
                Set.of(Direction.NORTH, Direction.SOUTH)
        );
        RoadUser roadUser = new TestRoadUser(Direction.SOUTH, Direction.NORTH);

        lane.addUser(roadUser);

        assertEquals(lane, roadUser.getLane());
        assertEquals(1, lane.getQueueSize());
    }

    @Test
    public void roadUserMovesTest() {
        TrafficLight trafficLight = new TestTrafficLight(TrafficLightState.GREEN);
        Lane lane = new TestLane(
                Set.of(LaneType.CAR),
                Direction.SOUTH,
                Set.of(Direction.NORTH, Direction.SOUTH),
                trafficLight
        );
        RoadUser roadUser = new TestRoadUser(Direction.SOUTH, Direction.NORTH);

        lane.addUser(roadUser);
        lane.moveUsers();

        assertEquals(0, lane.getQueueSize());
    }

    @Test
    public void roadUserDoesNotMoveTest() {
        TrafficLight trafficLight = new TestTrafficLight(TrafficLightState.RED);
        Lane lane = new TestLane(
                Set.of(LaneType.CAR),
                Direction.SOUTH,
                Set.of(Direction.NORTH, Direction.SOUTH),
                trafficLight
        );
        RoadUser roadUser = new TestRoadUser(Direction.SOUTH, Direction.NORTH);

        lane.addUser(roadUser);
        lane.moveUsers();

        assertEquals(1, lane.getQueueSize());
    }
}
