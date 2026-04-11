package sis.intersection;

import sis.lanes.Direction;
import sis.lanes.Lane;
import sis.users.RoadUser;

import java.util.*;

public class Intersection {
    Map<Direction, IntersectionSide> intersectionSides;
    List<RoadUser> exitedUsers;

    public Intersection() {
        intersectionSides = new HashMap<>();
        this.exitedUsers = new ArrayList<>();

        for (Direction d : Direction.values()) {
            intersectionSides.put(d, new IntersectionSide());
        }
    }

    public void addLane(Lane lane) {
        IntersectionSide entry = intersectionSides.get(lane.getEntry());
        entry.addLane(lane);
    }

    public Set<Lane> getAllLanes() {
        Set<Lane> lanes = new HashSet<>();
        for (IntersectionSide entry : intersectionSides.values()) {
            lanes.addAll(entry.getEntryLanes());
        }

        return lanes;
    }

    public boolean hasPedestrianTraffic(Direction side) {
        return intersectionSides.get(side).hasPedestrianTraffic();
    }

    public boolean hasExitTraffic(Direction side) {
        return intersectionSides.get(side).hasExitTraffic();
    }

    public void addExitedUser(RoadUser user) {
        this.exitedUsers.add(user);
    }

    public void reset() {
        this.exitedUsers.clear();

        for (IntersectionSide side : intersectionSides.values()) {
            side.resetValues();
        }
    }

    public IntersectionSide getIntersectionSide(Direction side) {
        return intersectionSides.get(side);
    }

    public Map<Direction, IntersectionSide> getIntersectionSides() {
        return intersectionSides;
    }

    public List<RoadUser> getExitedUsers() {
        return exitedUsers;
    }
}
