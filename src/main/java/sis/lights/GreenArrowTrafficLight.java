package sis.lights;

import java.util.List;

public class GreenArrowTrafficLight extends TrafficLight {

    @Override
    protected List<TrafficLightState> getRedTransition() {
        return List.of(
                TrafficLightState.YELLOW,
                TrafficLightState.RED_ARROW_GREEN
        );
    }

    @Override
    protected List<TrafficLightState> getGreenTransition() {
        return List.of(
                TrafficLightState.RED_YELLOW,
                TrafficLightState.GREEN
        );
    }

    @Override
    protected boolean isAlreadyRed() {
        return this.currentState == TrafficLightState.RED ||
                this.currentState == TrafficLightState.RED_ARROW_GREEN;
    }

}
