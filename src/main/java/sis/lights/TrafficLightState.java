package sis.lights;

import sis.visualizatoon.Color;

public enum TrafficLightState {
    RED(EntryType.FORBIDDEN, Color.RED),
    RED_YELLOW(EntryType.FORBIDDEN, Color.ORANGE),
    GREEN(EntryType.CARELESS, Color.GREEN),
    YELLOW(EntryType.FORBIDDEN, Color.YELLOW),
    RED_ARROW_GREEN(EntryType.GIVE_WAY, Color.BLUE),
    BLINK_GREEN(EntryType.FORBIDDEN, Color.GREEN);

    private final EntryType entryType;
    private final Color color;

    TrafficLightState(EntryType entryType, Color color) {
        this.entryType = entryType;
        this.color = color;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public Color getColor() {
        return color;
    }
}
