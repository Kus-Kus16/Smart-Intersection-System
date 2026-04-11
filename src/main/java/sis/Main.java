package sis;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import sis.intersection.Intersection;
import sis.lanes.*;
import sis.simulation.Simulation;
import sis.visualizatoon.ConsoleVisualizer;
import sis.visualizatoon.Visualizer;

public class Main {
    static void main() {
        Intersection intersection = new Intersection();

        for (Direction direction : Direction.values()) {
            Lane leftLane = new LeftLane(direction, intersection);
            intersection.addLane(leftLane);
            Lane straightRightLane = new StraightRightLane(direction, intersection);
            intersection.addLane(straightRightLane);
            Lane pedestrianLane = new PedestrianLane(direction, intersection);
            intersection.addLane(pedestrianLane);
        }

        Visualizer visualizer = new ConsoleVisualizer();
        Simulation simulation = new Simulation(intersection, visualizer);

        visualizer.visualize(intersection);
        for (int i = 0; i < 15; i++) {
            simulation.step();
        }
    }
}
