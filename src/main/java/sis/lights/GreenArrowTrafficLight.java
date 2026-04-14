package sis.lights;

import java.util.List;

public class GreenArrowTrafficLight extends TrafficLight {

    public GreenArrowTrafficLight() {
        super();
        this.currentState = TrafficLightState.RED_ARROW_GREEN;
    }

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
                TrafficLightState.COLLISION_GREEN
        );
    }

    @Override
    public boolean isCurrentlyRed() {
        return this.currentState == TrafficLightState.RED ||
                this.currentState == TrafficLightState.RED_ARROW_GREEN;
    }


    @Override
    public boolean isCurrentlyGreen() {
        return this.currentState == TrafficLightState.COLLISION_GREEN;
    }

}
