package sis.intersection;

import sis.lanes.Lane;

import java.util.ArrayList;
import java.util.List;

public class IntersectionEntry {
    List<Lane> lanes;

    public IntersectionEntry() {
        lanes = new ArrayList<>();
    }

    public void addLane(Lane lane) {
        lanes.add(lane);
    }

    public List<Lane> getLanes() {
        return lanes;
    }
}
