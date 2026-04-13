package sis.simulation.strategy;

import sis.lanes.Lane;

import java.util.List;

public record ActionGroupedLanes(
        List<Lane> greenLanes,
        List<Lane> redLanes,
        List<Lane> nonChangableLanes
) {
}
