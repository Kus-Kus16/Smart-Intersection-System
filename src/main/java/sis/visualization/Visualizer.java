package sis.visualization;

import sis.intersection.Intersection;
import sis.users.UserObserver;

public interface Visualizer extends UserObserver {
    void visualize(Intersection intersection);
}
