package sis.intersection;

import sis.lanes.Lane;
import sis.lanes.LaneType;

import java.util.*;

public class IntersectionSide {
    List<Lane> entryLanes;
    private final Set<LaneType> exitTraffic;

    public IntersectionSide() {
        entryLanes = new ArrayList<>();
        this.exitTraffic = new HashSet<>();
    }

    public void addLane(Lane lane) {
        entryLanes.add(lane);
    }

    public List<Lane> getEntryLanes() {
        return entryLanes;
    }

    public boolean hasExitTraffic(LaneType... types) {
        if (types.length == 0) {
            return !this.exitTraffic.isEmpty();
        }

        for (LaneType type : types) {
            if (this.exitTraffic.contains(type)) {
                return true;
            }
        }

        return false;
    }

    public void resetExitTraffic() {
        this.exitTraffic.clear();
    }

    public void occupyExit(Collection<LaneType> laneTypes) {
        this.exitTraffic.addAll(laneTypes);
    }
}
