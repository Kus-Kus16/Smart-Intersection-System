package sis.intersection;

import sis.lanes.Lane;
import sis.lanes.LaneType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntersectionSide {
    List<Lane> entryLanes;
    private boolean hasExitTraffic;
    private boolean hasPedestrianTraffic;

    public IntersectionSide() {
        entryLanes = new ArrayList<>();
        this.hasExitTraffic = false;
        this.hasPedestrianTraffic = false;
    }

    public void addLane(Lane lane) {
        entryLanes.add(lane);
    }

    public List<Lane> getEntryLanes() {
        return entryLanes;
    }

    public boolean hasExitTraffic() {
        return hasExitTraffic;
    }

    public boolean hasPedestrianTraffic() {
        return hasPedestrianTraffic;
    }

    public void resetValues() {
        this.hasExitTraffic = false;
        this.hasPedestrianTraffic = false;
    }

    public void occupyExit() {
        this.hasExitTraffic = true;
    }

    public void occupyPedestrian() {
        this.hasPedestrianTraffic = true;
    }
}
