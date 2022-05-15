package boardgame.models;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    @Test
    void squareProperty() {
        GameModel model = new GameModel();
        assertThrows(IndexOutOfBoundsException.class,() -> model.squareProperty(GameModel.BOARD_SIZE, GameModel.BOARD_SIZE));
        assertEquals(new Piece(PieceType.EMPTY, new Position(4, 4)).getType(), model.squareProperty(GameModel.BOARD_SIZE - 1, GameModel.BOARD_SIZE - 1).getValue().getType());
    }

    @Test
    void click() {
        GameModel model = new GameModel();
        model.click(0,0);
        assertEquals(model.getMoveCount(),1);
        assertEquals(model.squareProperty(0, 0).getValue().getType(), PieceType.RED);
    }

    @Test
    void getMoveCount() {
        GameModel model = new GameModel();
        assertEquals(0, model.getMoveCount());
        model.click(0,0);
        model.click(0,1);
        assertEquals(2, model.getMoveCount());
    }

    @Test
    void gameOver() {
        GameModel model = new GameModel();
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
        GameModel model = new GameModel();
        assertTrue(model.isOnBoard(0,0));
        assertFalse(model.isOnBoard(GameModel.BOARD_SIZE+1,GameModel.BOARD_SIZE+2));
        assertFalse(model.isOnBoard(-1,-1));
        assertFalse(model.isOnBoard(GameModel.BOARD_SIZE-1,GameModel.BOARD_SIZE+1));

    }

    @Test
    void checkCol() {
        GameModel model = new GameModel();
        assertThrows(ArrayIndexOutOfBoundsException.class,() -> model.checkCol(GameModel.BOARD_SIZE +1, PieceType.RED));
        model.click(0,0);
        assertFalse(model.checkCol(0,PieceType.RED));
        assertTrue(model.checkCol(0,PieceType.EMPTY));

    }

    @Test
    void checkRow() {
        GameModel model = new GameModel();
        assertThrows(ArrayIndexOutOfBoundsException.class,() -> model.checkRow(GameModel.BOARD_SIZE -6, PieceType.RED));
        model.click(0,0);
        assertFalse(model.checkRow(0,PieceType.RED));
        assertTrue(model.checkRow(0,PieceType.EMPTY));
    }

    @Test
    void checkLongDiagonal() {
        GameModel model = new GameModel();
        assertFalse(model.checkLongDiagonal(new Position(0, 1), PieceType.RED));

    }

    @Test
    void checkCell() {
        GameModel model = new GameModel();
        assertFalse(model.checkCell(new Position(-1,0), new Position(1,1), PieceType.RED));
        assertTrue(model.checkCell(new Position(0,0), new Position(1,1), PieceType.EMPTY));

    }

    @Test
    void checkShortDiagonal() {
        GameModel model = new GameModel();
        assertFalse(model.checkShortDiagonal(new Position(0, 1), PieceType.RED));
    }

}