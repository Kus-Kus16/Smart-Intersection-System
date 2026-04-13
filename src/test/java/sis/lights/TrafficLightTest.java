package sis.lights;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrafficLightTest {

    @Test
    public void emptyStepTest() {
        TrafficLight trafficLight = new TestTrafficLight();

        trafficLight.makeStep(0);

        assertEquals(TrafficLightState.RED, trafficLight.getCurrentState());
        assertEquals(0, trafficLight.getWaitingTime());
        assertTrue(trafficLight.isReadyToChange());
    }

    @Test
    public void trafficEmptyStepTest() {
        TrafficLight trafficLight = new TestTrafficLight();

        trafficLight.makeStep(3);
        assertEquals(TrafficLightState.RED, trafficLight.getCurrentState());
        assertEquals(1, trafficLight.getWaitingTime());
        assertTrue(trafficLight.isReadyToChange());
    }

    @Test
    public void greenTransitionTest() {
        TrafficLight trafficLight = new TestTrafficLight();

        trafficLight.queueGreen();

        assertEquals(TrafficLightState.RED, trafficLight.getCurrentState());
        assertFalse(trafficLight.isReadyToChange());
        assertFalse(trafficLight.isTransitioningToSafe());

        trafficLight.makeStep(3);

        assertEquals(TrafficLightState.RED_YELLOW, trafficLight.getCurrentState());
        assertEquals(1, trafficLight.getWaitingTime());
        assertFalse(trafficLight.isReadyToChange());
        assertFalse(trafficLight.isTransitioningToSafe());

        trafficLight.makeStep(3);

        assertEquals(TrafficLightState.GREEN, trafficLight.getCurrentState());
        assertEquals(0, trafficLight.getWaitingTime());
        assertTrue(trafficLight.isReadyToChange());
        assertFalse(trafficLight.isTransitioningToSafe());

        trafficLight.makeStep(2);

        assertEquals(TrafficLightState.GREEN, trafficLight.getCurrentState());
        assertEquals(1, trafficLight.getWaitingTime());
        assertTrue(trafficLight.isReadyToChange());
        assertFalse(trafficLight.isTransitioningToSafe());
    }

    @Test
    public void redTransitionTest() {
        TrafficLight trafficLight = new TestTrafficLight(TrafficLightState.GREEN);

        trafficLight.queueRed();

        assertEquals(TrafficLightState.GREEN, trafficLight.getCurrentState());
        assertFalse(trafficLight.isReadyToChange());
        assertTrue(trafficLight.isTransitioningToSafe());

        trafficLight.makeStep(3);

        assertEquals(TrafficLightState.YELLOW, trafficLight.getCurrentState());
        assertEquals(1, trafficLight.getWaitingTime());
        assertFalse(trafficLight.isReadyToChange());
        assertTrue(trafficLight.isTransitioningToSafe());

        trafficLight.makeStep(3);

        assertEquals(TrafficLightState.RED, trafficLight.getCurrentState());
        assertEquals(0, trafficLight.getWaitingTime());
        assertTrue(trafficLight.isReadyToChange());
        assertFalse(trafficLight.isTransitioningToSafe());

        trafficLight.makeStep(2);

        assertEquals(TrafficLightState.RED, trafficLight.getCurrentState());
        assertEquals(1, trafficLight.getWaitingTime());
        assertTrue(trafficLight.isReadyToChange());
        assertFalse(trafficLight.isTransitioningToSafe());
    }

    @Test
    public void throwsIllegalStateonFullQueueTest() {
        TrafficLight trafficLight = new TestTrafficLight();

        trafficLight.queueGreen();

        assertThrows(IllegalStateException.class, trafficLight::queueRed);
    }

    @Test
    public void doesNotQueueIfInStateTest() {
        TrafficLight trafficLight = new TestTrafficLight();

        trafficLight.queueRed();

        assertEquals(TrafficLightState.RED, trafficLight.getCurrentState());

        trafficLight.makeStep(1);

        assertEquals(TrafficLightState.RED, trafficLight.getCurrentState());
    }
}
