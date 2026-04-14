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
import java.util.Comparator;
import java.util.List;
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
            changeLights(actionGroupedLanes);
            moveUsers();

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

    private void changeLights(ActionGroupedLanes lanes) {
        for (Lane lane : lanes.nonChangableLanes()) {
            lane.nextLight();
        }
        for (Lane lane : lanes.greenLanes()) {
            lane.nextLight();
        }
        for (Lane lane : lanes.redLanes()) {
            lane.nextLight();
        }
    }

    private void moveUsers() {
        List<Lane> lanes = intersection.getAllLanes();
        lanes.sort(Comparator.comparingInt(Lane::getMovePriority).reversed());
        for (Lane lane : lanes) {
            lane.moveUsers();
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
