package sis;

public enum Direction {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public static Direction leftFrom(Direction direction) {
        return fromValue((direction.value + 1) % 4);
    }

    public static Direction rightFrom(Direction direction) {
        return fromValue((direction.value + 3) % 4);
    }

    public static Direction straightFrom(Direction direction) {
        return fromValue((direction.value + 2) % 4);
    }

    private static Direction fromValue(Integer value) {
        for (Direction dir : values()) {
            if (dir.value == value) {
                return dir;
            }
        }
        throw new IllegalArgumentException();
    }
}
