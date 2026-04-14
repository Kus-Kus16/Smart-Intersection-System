package sis.lights;

import java.util.List;

public class TestTrafficLight extends TrafficLight {
    public TestTrafficLight() {
        super();
    }

    public TestTrafficLight(TrafficLightState initialState) {
        super();
        this.currentState = initialState;
    }

    @Override
    protected List<TrafficLightState> getRedTransition() {
        return List.of(TrafficLightState.YELLOW, TrafficLightState.RED);
    }

    @Override
    protected List<TrafficLightState> getGreenTransition() {
        return List.of(TrafficLightState.RED_YELLOW, TrafficLightState.GREEN);
    }

    @Override
    public boolean isCurrentlyGreen() {
        return this.currentState == TrafficLightState.GREEN;
    }
}
