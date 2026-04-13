package sis.users;

import org.junit.jupiter.api.Test;
import sis.lanes.Direction;
import sis.lights.TrafficLightState;

import static org.junit.jupiter.api.Assertions.*;

public class PedestrianTest {
    @Test
    public void canMoveTest() {
        Pedestrian pedestrian = new Pedestrian(Direction.NORTH, Direction.NORTH, "");

        assertTrue(pedestrian.canMove(TrafficLightState.GREEN, null));
        assertFalse(pedestrian.canMove(TrafficLightState.RED, null));
        assertThrows(IllegalStateException.class, () -> pedestrian.canMove(TrafficLightState.RED_ARROW_GREEN, null));
    }
}
