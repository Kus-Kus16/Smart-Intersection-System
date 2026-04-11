package sis.lights;

import sis.visualizatoon.Color;

import java.util.LinkedList;
import java.util.List;
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

    public void queueRed() {
        queueState(getRedTransition(), isAlreadyRed());
    }

    public void queueGreen() {
        queueState(getGreenTransition(), isAlreadyGreen());
    }

    private void queueState(List<TrafficLightState> steps, boolean alreadyInState) {
        if (!isReadyToChange()) {
            throw new IllegalStateException();
        }

        if (alreadyInState) {
            return;
        }

        this.statesQueue.addAll(steps);
    }

    protected abstract List<TrafficLightState> getRedTransition();
    protected abstract List<TrafficLightState> getGreenTransition();

    protected boolean isAlreadyRed() {
        return this.currentState == TrafficLightState.RED;
    }

    protected boolean isAlreadyGreen() {
        return this.currentState == TrafficLightState.GREEN;
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

    public TrafficLightState getCurrentState() {
        return currentState;
    }

    public Color getColor() {
        return this.currentState.getColor();
    }
}
