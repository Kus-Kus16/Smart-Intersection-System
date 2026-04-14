package sis.lights;

public enum EntryType {
    CARELESS(4),
    CAUTION(3),
    FORBIDDEN(1),
    GIVE_WAY_ARROW(2);

    private final int movePriority;

    EntryType(int movePriority) {
        this.movePriority = movePriority;
    }

    public int getMovePriority() {
        return movePriority;
    }
}
