package sis.simulation.commands;

import sis.simulation.Simulation;

public class StepCommand extends Command{

    @Override
    public void execute(Simulation simulation) {
        simulation.step();
    }
}
