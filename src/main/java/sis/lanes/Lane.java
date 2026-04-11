package sis.lanes;

import sis.users.RoadUser;
import sis.conditions.Condition;
import sis.intersection.Intersection;
import sis.lights.TrafficLight;
import sis.visualizatoon.Color;

import java.util.*;

public abstract class Lane {
    protected final LaneType laneType;
    protected Queue<RoadUser> queue;
    protected Direction entry;
    protected Set<Direction> exits;
    protected Intersection intersection;
    protected TrafficLight trafficLight;

    protected final List<Condition> conflictConditions;

    public Lane(LaneType laneType, Direction entry, Collection<Direction> exits, Intersection intersection) {
        this.laneType = laneType;
        this.entry = entry;
        this.exits = new HashSet<>(exits);
        this.intersection = intersection;

        this.queue = new LinkedList<>();
        this.conflictConditions = generateConflictConditions();
    }

    protected abstract List<Condition> generateConflictConditions();

    public void addCar(RoadUser roadUser) {
        queue.add(roadUser);
    }

    public boolean isReadyToChange() {
        return this.trafficLight.isReadyToChange();
    }

    public void queueGreenLight() {
        System.out.println("Queueing green light " + this.entry + " " + this.getClass().getSimpleName());
        this.trafficLight.queueGreen();
    }

    public void queueRedLight() {
        System.out.println("Queueing red light " + this.entry + " " + this.getClass().getSimpleName());
        this.trafficLight.queueRed();
    }

    public void doNotChange() { //TODO remove
        System.out.println("Cannot change " + this.entry + " " + this.getClass().getSimpleName());
    }

    public void makeStep() {
        this.trafficLight.makeStep();
        if (this.queue.isEmpty()) {
            return;
        }

        RoadUser roadUser = queue.peek();
        if (roadUser.canMove(this.trafficLight.getCurrentState(), this.intersection)) {
            queue.poll();
            roadUser.exit(this.intersection);
            intersection.addExitedUser(roadUser);
        }
    }


    public Direction getEntry() {
        return entry;
    }

    public Set<Direction> getExits() {
        return exits;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public List<Condition> getConflictConditions() {
        return conflictConditions;
    }

    public LaneType getLaneType() {
        return laneType;
    }

    public int getQueueSize() {
        return queue.size();
    }

    protected abstract String getRepresentation();

    @Override
    public String toString() {
        return this.trafficLight.getCurrentState() + this.getRepresentation() +
                this.getQueueSize();
    }

    public Color getColor() {
        return this.trafficLight.getColor();
    }
}
