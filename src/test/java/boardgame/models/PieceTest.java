package boardgame.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {
    @Test
    void testToString() {
        Piece piece = new Piece(PieceType.RED, new Position(0, 0));
        assertEquals("RED(0,0)", piece.toString());
    }
}