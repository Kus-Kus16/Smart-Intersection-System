package sis.lanes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {
    @Test
    void leftFromTest() {
        assertEquals(Direction.EAST, Direction.leftFrom(Direction.NORTH));
        assertEquals(Direction.SOUTH, Direction.leftFrom(Direction.EAST));
        assertEquals(Direction.WEST, Direction.leftFrom(Direction.SOUTH));
        assertEquals(Direction.NORTH, Direction.leftFrom(Direction.WEST));
    }

    @Test
    void rightFromTest() {
        assertEquals(Direction.WEST, Direction.rightFrom(Direction.NORTH));
        assertEquals(Direction.NORTH, Direction.rightFrom(Direction.EAST));
        assertEquals(Direction.EAST, Direction.rightFrom(Direction.SOUTH));
        assertEquals(Direction.SOUTH, Direction.rightFrom(Direction.WEST));
    }

    @Test
    void straightFromTest() {
        assertEquals(Direction.SOUTH, Direction.straightFrom(Direction.NORTH));
        assertEquals(Direction.WEST, Direction.straightFrom(Direction.EAST));
        assertEquals(Direction.NORTH, Direction.straightFrom(Direction.SOUTH));
        assertEquals(Direction.EAST, Direction.straightFrom(Direction.WEST));
    }

    @Test
    void cycleTest() {
        Direction direction = Direction.NORTH;

        for (int i = 0; i < 4; i++) {
            direction = Direction.leftFrom(direction);
        }

        assertEquals(Direction.NORTH, direction);
    }

}
