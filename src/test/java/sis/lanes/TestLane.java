package sis.lanes;

import sis.lanes.conditions.Condition;
import sis.lights.TrafficLight;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class TestLane extends Lane {

    public TestLane(Set<LaneType> laneTypes, Direction entry, Collection<Direction> exits) {
        super(laneTypes, entry, exits, null);
    }

    public TestLane(Set<LaneType> laneTypes, Direction entry, Collection<Direction> exits, TrafficLight trafficLight) {
        this(laneTypes, entry, exits);
        this.trafficLight = trafficLight;
    }

    @Override
    protected List<Condition> generateConflictConditions() {
        return List.of();
    }

    @Override
    protected String getRepresentation() {
        return "";
    }
}
