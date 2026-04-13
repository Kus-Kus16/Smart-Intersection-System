package sis.simulation;

import sis.simulation.commands.Command;
import sis.intersection.Intersection;
import sis.lanes.Lane;
import sis.simulation.strategy.ActionGroupedLanes;
import sis.simulation.strategy.SimulationStrategy;
import sis.users.RoadUser;
import sis.util.CommandReader;
import sis.util.ResultWriter;
import sis.util.visualization.Visualizer;

import java.io.EOFException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation {
    private final Intersection intersection;
    private final Visualizer visualizer;
    private final CommandReader commandReader;
    private final ResultWriter resultWriter;
    private final SimulationStrategy strategy;
    private final Logger logger =  Logger.getLogger("soutLogger");

    public Simulation(Intersection intersection, Visualizer visualizer, CommandReader commandReader,
                      ResultWriter resultWriter, SimulationStrategy strategy) {
        this.intersection = intersection;
        this.visualizer = visualizer;
        this.commandReader = commandReader;
        this.resultWriter = resultWriter;
        this.strategy = strategy;
    }

    public void run() throws IOException {
        commandReader.open();
        resultWriter.open();

        while (true) {
            try {
                Command command = commandReader.readNextCommand();
                command.execute(this);
            } catch (EOFException e) {
                commandReader.close();
                resultWriter.close();
                return;
            }
        }
    }

    public void step() {
        try {
            intersection.reset();
            resultWriter.startStep();
            visualizer.beforeStep(intersection);

            ActionGroupedLanes actionGroupedLanes = strategy.groupLanes(intersection.getAllLanes());
            queueLightChanges(actionGroupedLanes);
            moveLanes(actionGroupedLanes);

            visualizer.afterStep(intersection);
            resultWriter.endStep();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IO exception during step: " + e.getMessage());
        }
    }

    private void queueLightChanges(ActionGroupedLanes lanes) {
        for (Lane lane : lanes.greenLanes()) {
            lane.queueGreenLight();
        }
        for (Lane lane : lanes.redLanes()) {
            lane.queueRedLight();
        }
    }

    private void moveLanes(ActionGroupedLanes lanes) {
        for (Lane lane : lanes.nonChangableLanes()) {
            lane.makeStep();
        }
        for (Lane lane : lanes.greenLanes()) {
            lane.makeStep();
        }
        for (Lane lane : lanes.redLanes()) {
            lane.makeStep();
        }
    }

    public void addUser(RoadUser user) {
        this.intersection.addUser(user);
        user.addObserver(resultWriter);
    }

    public Intersection getIntersection() {
        return intersection;
    }

}
