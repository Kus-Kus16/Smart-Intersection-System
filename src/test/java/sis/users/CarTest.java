package sis.users;

import org.junit.jupiter.api.Test;
import sis.intersection.Intersection;
import sis.lanes.Direction;
import sis.lanes.LaneType;
import sis.lights.TrafficLightState;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    @Test
    public void canMoveTest() {
        Car car = new Car(Direction.SOUTH, Direction.NORTH, "");

        assertTrue(car.canMove(TrafficLightState.GREEN, null));
        assertFalse(car.canMove(TrafficLightState.RED, null));
        assertFalse(car.canMove(TrafficLightState.RED_ARROW_GREEN, null));

    }

    @Test
    public void canMoveOnArrowTest() {
        Intersection intersectionClear = new Intersection();
        Intersection intersectionCarSouth = new Intersection();
        Intersection intersectionPedestrianSouth = new Intersection();
        Intersection intersectionCarEast = new Intersection();
        Intersection intersectionPedestrianEast = new Intersection();
        Car carRight = new Car(Direction.SOUTH, Direction.EAST, "");

        intersectionCarSouth.getIntersectionSide(Direction.SOUTH).occupyExit(Set.of(LaneType.CAR));
        intersectionPedestrianSouth.getIntersectionSide(Direction.SOUTH).occupyExit(Set.of(LaneType.PEDESTRIAN));
        intersectionCarEast.getIntersectionSide(Direction.EAST).occupyExit(Set.of(LaneType.CAR));
        intersectionPedestrianEast.getIntersectionSide(Direction.EAST).occupyExit(Set.of(LaneType.PEDESTRIAN));


        assertTrue(carRight.canMove(TrafficLightState.RED_ARROW_GREEN, intersectionClear));
        assertTrue(carRight.canMove(TrafficLightState.RED_ARROW_GREEN, intersectionCarSouth));
        assertFalse(carRight.canMove(TrafficLightState.RED_ARROW_GREEN, intersectionPedestrianSouth));
        assertFalse(carRight.canMove(TrafficLightState.RED_ARROW_GREEN, intersectionCarEast));
        assertFalse(carRight.canMove(TrafficLightState.RED_ARROW_GREEN, intersectionPedestrianEast));
    }
}
