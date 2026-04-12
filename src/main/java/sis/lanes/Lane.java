package sis.lanes;

import sis.users.RoadUser;
import sis.conditions.Condition;
import sis.intersection.Intersection;
import sis.lights.TrafficLight;
import sis.visualization.Color;

import java.util.*;

public abstract class Lane {
    protected final Set<LaneType> laneTypes;
    protected Queue<RoadUser> queue;
    protected Direction entry;
    protected Set<Direction> exits;
    protected Intersection intersection;
    protected TrafficLight trafficLight;

    protected final List<Condition> conflictConditions;

    public Lane(Set<LaneType> laneTypes, Direction entry, Collection<Direction> exits, Intersection intersection) {
        this.laneTypes = laneTypes;
        this.entry = entry;
        this.exits = new HashSet<>(exits);
        this.intersection = intersection;

        this.queue = new LinkedList<>();
        this.conflictConditions = generateConflictConditions();
    }

    protected abstract List<Condition> generateConflictConditions();

    public void addUser(RoadUser roadUser) {
        queue.add(roadUser);
        roadUser.setLane(this);
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
        }
    }


    public Direction getEntry() {
        return entry;
    }

    public Set<Direction> getExits() {
        return exits;
    }

    public boolean hasExitOn(Direction direction) {
        return exits.contains(direction);
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public List<Condition> getConflictConditions() {
        return conflictConditions;
    }

    public Set<LaneType> getLaneTypes() {
        return laneTypes;
    }

    public boolean isLaneType(LaneType laneType) {
        return laneTypes.contains(laneType);
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
