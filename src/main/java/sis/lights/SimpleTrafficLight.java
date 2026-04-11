package sis.lights;

import java.util.List;

public class SimpleTrafficLight extends TrafficLight {

    @Override
    protected List<TrafficLightState> getRedTransition() {
        return List.of(
                TrafficLightState.YELLOW,
                TrafficLightState.RED
        );
    }

    @Override
    protected List<TrafficLightState> getGreenTransition() {
        return List.of(
                TrafficLightState.RED_YELLOW,
                TrafficLightState.GREEN
        );
    }
}
