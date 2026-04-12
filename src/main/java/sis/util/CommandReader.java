package sis.util;

import sis.commands.AddRoadUserCommand;
import sis.commands.Command;
import sis.commands.StepCommand;

import java.io.EOFException;
import java.io.IOException;
import java.util.Map;

public abstract class CommandReader {
    public abstract void open() throws IOException;
    public abstract void close() throws IOException;
    public abstract Command readNextCommand() throws IOException, EOFException;

    protected static final Map<String, Class<? extends Command>> COMMAND_MAP = Map.of(
            "addRoadUser", AddRoadUserCommand.class,
            "step", StepCommand.class
    );
}
