package sis;

import sis.intersection.Intersection;
import sis.lanes.*;
import sis.simulation.Simulation;
import sis.simulation.strategy.SimulationStrategy;
import sis.simulation.strategy.StrategyPriority;
import sis.util.CommandReader;
import sis.util.JsonCommandReader;
import sis.util.JsonResultWriter;
import sis.util.ResultWriter;
import sis.util.visualization.ConsoleVisualizer;
import sis.util.visualization.Visualizer;

import java.io.IOException;

public class Main {
    static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Not enough arguments: <inputFilename> <outputFilename>");
            return;
        }

        String inputFilename = args[0];
        String outputFilename = args[1];
        boolean colorEnabled = true;

        if (args.length >= 3) {
            colorEnabled = Boolean.parseBoolean(args[2]);
        }

        Intersection intersection = new Intersection();

        for (Direction direction : Direction.values()) {
            Lane leftLane = new LeftLane(direction, intersection);
            intersection.addLane(leftLane);
            Lane straightRightLane = new StraightRightLane(direction, intersection);
            intersection.addLane(straightRightLane);
            Lane pedestrianLane = new PedestrianLane(direction, intersection);
            intersection.addLane(pedestrianLane);
        }

        Visualizer visualizer = new ConsoleVisualizer(colorEnabled);
        CommandReader commandReader = new JsonCommandReader(inputFilename);
        ResultWriter resultWriter = new JsonResultWriter(outputFilename);
        SimulationStrategy strategy = new StrategyPriority();

        Simulation simulation = new Simulation(intersection, visualizer, commandReader, resultWriter, strategy);

        simulation.run();
    }
}
