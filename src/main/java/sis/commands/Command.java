package sis.commands;

import sis.simulation.Simulation;

public abstract class Command {
    private String type;

    public abstract void execute(Simulation simulation);

    public void setType(String type) {
        this.type = type;
    }
}
