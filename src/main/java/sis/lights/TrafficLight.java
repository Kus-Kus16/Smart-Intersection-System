package sis.lights;

import java.util.LinkedList;
import java.util.Queue;

public abstract class TrafficLight {
    protected final static int MIN_SKIPPED_STEPS = 1;
    protected TrafficLightState currentState;
    protected Queue<TrafficLightState> statesQueue;
    protected int skippedSteps;

    public TrafficLight() {
        this.statesQueue = new LinkedList<>();
        this.currentState = TrafficLightState.RED;
        this.skippedSteps = 0;
    }

    public void queueRed() throws IllegalStateException {
        if (!isReadyToChange()) {
            throw new IllegalStateException();
        }
    }
    public void queueGreen() throws IllegalStateException{
        if (!isReadyToChange()) {
            throw new IllegalStateException();
        }
    }

    public boolean isReadyToChange() {
        if (!this.statesQueue.isEmpty()) {
            return false;
        }

        if (this.currentState == TrafficLightState.GREEN && this.skippedSteps < MIN_SKIPPED_STEPS) {
            return false;
        }

        return true;
    }

    public void makeStep() {
        if (this.statesQueue.isEmpty()) {
            this.skippedSteps++;
            return;
        }

        this.skippedSteps = 0;
        this.currentState = statesQueue.poll();
    }
}
