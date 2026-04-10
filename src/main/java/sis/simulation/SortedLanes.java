package sis.simulation;

import sis.lanes.Lane;

import java.util.List;

public record SortedLanes(
        List<Lane> greenLanes,
        List<Lane> redLanes
) {
}
