package sis.lights;

import sis.util.visualization.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class TrafficLight {
    protected TrafficLightState currentState;
    protected Queue<TrafficLightState> statesQueue;
    protected int waitingTime;
    protected boolean transitioningToSafe;

    public TrafficLight() {
        this.statesQueue = new LinkedList<>();
        this.currentState = TrafficLightState.RED;
        this.waitingTime = 0;
        this.transitioningToSafe = false;
    }

    public void queueRed() {
        queueState(getRedTransition(), isCurrentlyRed(), true);
    }

    public void queueGreen() {
        queueState(getGreenTransition(), isCurrentlyGreen(), false);
    }

    private void queueState(List<TrafficLightState> steps, boolean alreadyInState, boolean isSafeState) {
        if (!isReadyToChange()) {
            throw new IllegalStateException();
        }

        if (alreadyInState) {
            return;
        }

        this.statesQueue.addAll(steps);
        this.transitioningToSafe = isSafeState;
    }

    protected abstract List<TrafficLightState> getRedTransition();
    protected abstract List<TrafficLightState> getGreenTransition();

    public boolean isCurrentlyRed() {
        return this.currentState == TrafficLightState.RED;
    }
    public abstract boolean isCurrentlyGreen();

    public boolean isReadyToChange() {
        return this.statesQueue.isEmpty();
    }

    public boolean isTransitioningToSafe() {
        if (this.isReadyToChange()) {
            return false;
        }

        return this.transitioningToSafe;
    }

    public void makeStep(int trafficQueueSize) {
        if (trafficQueueSize == 0 && !isCurrentlyGreen()) {
            this.waitingTime = 0;
        } else {
            this.waitingTime++;
        }

        if (this.statesQueue.isEmpty()) {
            return;
        }

        this.currentState = statesQueue.poll();
        if (this.statesQueue.isEmpty()) {
            this.waitingTime = 0;
            this.transitioningToSafe = false;
        }
    }

    public TrafficLightState getCurrentState() {
        return currentState;
    }

    public Color getColor() {
        return this.currentState.getColor();
    }

    public int getWaitingTime() {
        return waitingTime;
    }
}
