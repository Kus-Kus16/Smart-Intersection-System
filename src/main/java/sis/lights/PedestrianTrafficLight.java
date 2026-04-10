package sis.lights;

import java.util.List;

public class PedestrianTrafficLight extends TrafficLight {

    @Override
    public void queueRed() throws IllegalStateException {
        super.queueRed();
        this.statesQueue.addAll(List.of(
                TrafficLightState.BLINK_GREEN,
                TrafficLightState.RED
        ));
    }

    @Override
    public void queueGreen() throws IllegalStateException {
        super.queueGreen();
        this.statesQueue.addAll(List.of(
                TrafficLightState.RED,
                TrafficLightState.GREEN
        ));
    }
}
