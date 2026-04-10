package sis.lanes;

import sis.Car;
import sis.Direction;
import sis.conditions.Condition;
import sis.intersection.Intersection;
import sis.lights.TrafficLight;

import java.util.*;

public abstract class Lane {
    protected final LaneType laneType;
    protected Queue<Car> queue;
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

    public void addCar(Car car) {
        queue.add(car);
    }

    public boolean isReadyToChange() {
        return this.trafficLight.isReadyToChange();
    }

    public void queueGreenLight() {
        this.trafficLight.queueGreen();
    }

    public void queueRedLight() {
        this.trafficLight.queueRed();
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

    @Override
    public String toString() {
        return "Lane{" +
                "laneType=" + laneType +
                ", entry=" + entry +
                ", exits=" + exits +
                '}';
    }
}
