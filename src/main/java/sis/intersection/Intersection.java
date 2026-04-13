package sis.intersection;

import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.lanes.LaneType;
import sis.users.RoadUser;

import java.util.*;

public class Intersection {
    Map<Direction, IntersectionSide> intersectionSides;

    public Intersection() {
        intersectionSides = new HashMap<>();

        for (Direction d : Direction.values()) {
            intersectionSides.put(d, new IntersectionSide());
        }
    }

    public void addLane(Lane lane) {
        IntersectionSide entry = intersectionSides.get(lane.getEntry());
        entry.addLane(lane);
    }

    public void addUser(RoadUser user) {
        IntersectionSide side = intersectionSides.get(user.getEntryDirection());
        for (Lane lane : side.getEntryLanes()) {
            if (lane.isLaneType(user.getExpectedLaneType()) && lane.hasExitOn(user.getExitDirection())) {
                lane.addUser(user);
                return;
            }
        }

        throw new IllegalStateException("No suitable lane");
    }

    public List<Lane> getAllLanes() {
        List<Lane> lanes = new LinkedList<>();
        for (IntersectionSide entry : intersectionSides.values()) {
            lanes.addAll(entry.getEntryLanes());
        }

        return lanes;
    }

    public boolean hasExitTraffic(Direction direction, LaneType... types) {
        return intersectionSides.get(direction).hasExitTraffic(types);
    }

    public void reset() {
        for (IntersectionSide side : intersectionSides.values()) {
            side.resetExitTraffic();
        }
    }

    public IntersectionSide getIntersectionSide(Direction side) {
        return intersectionSides.get(side);
    }

}
