package sis.intersection;

import sis.Direction;
import sis.lanes.Lane;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Intersection {
    Map<Direction, IntersectionEntry> entries;

    public Intersection() {
        entries = new HashMap<>();

        for (Direction d : Direction.values()) {
            entries.put(d, new IntersectionEntry());
        }
    }

    public void addLane(Lane lane) {
        IntersectionEntry entry = entries.get(lane.getEntry());
        entry.addLane(lane);
    }

    public Set<Lane> getAllLanes() {
        Set<Lane> lanes = new HashSet<>();
        for (IntersectionEntry entry : entries.values()) {
            lanes.addAll(entry.getLanes());
        }

        return lanes;
    }
}
