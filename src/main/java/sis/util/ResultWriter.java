package sis.util;

import sis.users.RoadUser;
import sis.users.UserObserver;

import java.io.IOException;

public abstract class ResultWriter implements UserObserver {
    public abstract void open() throws IOException;
    public abstract void close() throws IOException;
    public abstract void startStep() throws IOException;
    public abstract void endStep() throws IOException;
}
