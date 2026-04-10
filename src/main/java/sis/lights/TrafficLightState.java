package sis.lights;

public enum TrafficLightState {
    RED(EntryType.FORBIDDEN),
    RED_YELLOW(EntryType.FORBIDDEN),
    GREEN(EntryType.CARELESS),
    YELLOW(EntryType.FORBIDDEN),
    RED_ARROW_GREEN(EntryType.GIVE_WAY),
    BLINK_GREEN(EntryType.FORBIDDEN);

    private final EntryType entryType;

    TrafficLightState(EntryType entryType) {
        this.entryType = entryType;
    }

    public EntryType getEntryType() {
        return entryType;
    }
}
