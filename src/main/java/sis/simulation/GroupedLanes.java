package sis.simulation;

import sis.lanes.Lane;

import java.util.List;

public record GroupedLanes(
        List<Lane> greenLanes,
        List<Lane> redLanes,
        List<Lane> nonChangableLanes
) {
}
