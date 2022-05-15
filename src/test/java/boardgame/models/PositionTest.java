package boardgame.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void moveTo() {
        Position pos = new Position(0, 0);

        Position state1 = pos.moveTo(PawnDirection.DOWN_RIGHT);
        assertEquals(1, state1.row());
        assertEquals(1, state1.col());

        Position state2 = pos.moveTo(PawnDirection.DOWN_LEFT);
        assertEquals(1, state2.row());
        assertEquals(-1, state2.col());

        Position state3 = pos.moveTo(PawnDirection.UP_RIGHT);
        assertEquals(-1, state3.row());
        assertEquals(1, state3.col());

        Position state4 = pos.moveTo(PawnDirection.UP_LEFT);
        assertEquals(-1, state4.row());
        assertEquals(-1, state4.col());
    }

    @Test
    void testToString() {
        String posStr = new Position(0, 0).toString();
        assertEquals("(0,0)", posStr);
    }
}