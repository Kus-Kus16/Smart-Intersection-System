package sis.lights;

import java.util.List;

public class PedestrianTrafficLight extends TrafficLight {

    @Override
    protected List<TrafficLightState> getRedTransition() {
        return List.of(
                TrafficLightState.BLINK_GREEN,
                TrafficLightState.RED
        );
    }

    @Override
    protected List<TrafficLightState> getGreenTransition() {
        return List.of(
                TrafficLightState.RED,
                TrafficLightState.GREEN
        );
    }

    @Override
    public boolean isCurrentlyGreen() {
        return this.currentState == TrafficLightState.GREEN;
    }
}
