package sis.lights;

import java.util.List;

public class GreenArrowTrafficLight extends TrafficLight {

    @Override
    public void queueRed() throws IllegalStateException {
        super.queueRed();
        this.statesQueue.addAll(List.of(
                TrafficLightState.YELLOW,
                TrafficLightState.RED_ARROW_GREEN
        ));
    }

    @Override
    public void queueGreen() throws IllegalStateException {
        super.queueGreen();
        this.statesQueue.addAll(List.of(
                TrafficLightState.RED_YELLOW,
                TrafficLightState.GREEN
        ));
    }
}
