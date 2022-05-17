package boardgame.models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {


    GameModel model;

    @BeforeEach
    void setUp() {
        model = new GameModel();
    }

    @Test
    void squareProperty() {
        assertThrows(IndexOutOfBoundsException.class,() -> model.squareProperty(GameModel.BOARD_SIZE, GameModel.BOARD_SIZE));
        assertEquals(new Piece(PieceType.EMPTY, new Position(4, 4)).getType(), model.squareProperty(GameModel.BOARD_SIZE - 1, GameModel.BOARD_SIZE - 1).getValue().getType());
    }

    @Test
    void click() {
        model.click(0,0);
        assertEquals(model.getMoveCount(),1);
        assertEquals(model.squareProperty(0, 0).getValue().getType(), PieceType.RED);
    }

    @Test
    void getMoveCount() {
        assertEquals(0, model.getMoveCount());
        model.click(0,0);
        model.click(0,1);
        assertEquals(2, model.getMoveCount());
    }

    @Test
    void gameOver() {
        model.click(0,0);
        Position pos = new Position(0,0);
        assertFalse(model.gameOver(pos, PieceType.RED));
        model.click(0,1);
        model.click(1,1);
        model.click(1,0);
        model.click(2,2);
        assertTrue(model.gameOver(pos, PieceType.RED));

    }

    @Test
    void isOnBoard() {
        assertTrue(model.isOnBoard(0,0));
        assertFalse(model.isOnBoard(GameModel.BOARD_SIZE+1,GameModel.BOARD_SIZE+2));
        assertFalse(model.isOnBoard(-1,-1));
        assertFalse(model.isOnBoard(GameModel.BOARD_SIZE-1,GameModel.BOARD_SIZE+1));

    }

    @Test
    void checkCol() {
        assertThrows(ArrayIndexOutOfBoundsException.class,() -> model.checkCol(GameModel.BOARD_SIZE +1, PieceType.RED));
        model.click(0,0);
        assertFalse(model.checkCol(0,PieceType.RED));
        assertTrue(model.checkCol(0,PieceType.EMPTY));

    }

    @Test
    void checkRow() {
        assertThrows(ArrayIndexOutOfBoundsException.class,() -> model.checkRow(GameModel.BOARD_SIZE -6, PieceType.RED));
        model.click(0,0);
        assertFalse(model.checkRow(0,PieceType.RED));
        assertTrue(model.checkRow(0,PieceType.EMPTY));
    }

    @Test
    void checkLongDiagonal() {
        assertFalse(model.checkLongDiagonal(new Position(0, 1), PieceType.RED));

    }

    @Test
    void checkCell() {
        assertFalse(model.checkCell(new Position(-1,0), new Position(1,1), PieceType.RED));
        assertTrue(model.checkCell(new Position(0,0), new Position(1,1), PieceType.EMPTY));

    }

    @Test
    void checkShortDiagonal() {
        assertFalse(model.checkShortDiagonal(new Position(0, 1), PieceType.RED));
    }

}