package sis;

import sis.intersection.Intersection;
import sis.lanes.*;
import sis.simulation.Simulation;
import sis.util.CommandReader;
import sis.util.JsonCommandReader;
import sis.util.JsonResultWriter;
import sis.util.ResultWriter;
import sis.visualization.ConsoleVisualizer;
import sis.visualization.Visualizer;

import java.io.IOException;

public class Main {
    static void main() throws IOException {
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
        CommandReader commandReader = new JsonCommandReader("commands.json");
        ResultWriter resultWriter = new JsonResultWriter("results.json");
        Simulation simulation = new Simulation(intersection, visualizer, commandReader, resultWriter);

        visualizer.visualize(intersection);
        simulation.run();
    }
}
