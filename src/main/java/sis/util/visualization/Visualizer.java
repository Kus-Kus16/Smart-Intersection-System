package sis.util.visualization;

import sis.intersection.Intersection;
import sis.users.UserObserver;

public interface Visualizer {
    void beforeStep(Intersection intersection);
    void afterStep(Intersection intersection);
}
