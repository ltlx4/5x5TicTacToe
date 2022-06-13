package boardgame.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The GameModel class is the model for the game.
 */
public class GameModel {

    /**
     * Board size.
     */
    public static int BOARD_SIZE = 5;
    /**
     * Logger for the class.
     */
    private static final Logger logger = LogManager.getLogger();

    private final ReadOnlyObjectWrapper<Piece>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];


    private int moveCount = 0;


    /**
     * Constructor for the GameModel.
     */
    public GameModel() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                var pos = new Position(i, j);
                board[i][j] = new ReadOnlyObjectWrapper<>(new Piece(PieceType.EMPTY, pos));
            }
        }

    }


    /**
     * Returns property object of the square at the given position.
     * @param i row
     * @param j column
     * @return {@link ReadOnlyObjectProperty} of the square at the given position
     */
    public ReadOnlyObjectProperty<Piece> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }


    /**
     * Clicks the square at the given position.
     * @param i row
     * @param j column
     */
    public void click(int i, int j) {
        var current = CurrentPlayer();

        switch (current) {
            case RED -> board[i][j].set(new Piece(PieceType.RED, new Position(i, j)));
            case BLUE -> board[i][j].set(new Piece(PieceType.BLUE, new Position(i, j)));
        }
        moveCount++;
        Position pos = new Position(i, j);
        gameOver(pos, current);

    }


    /**
     * Returns the number of total moves made.
     * @return int the number of total moves made
     */
    public int getMoveCount() {
        return moveCount;
    }


    /**
     * Checks if the game is over.
     * @param pos position of the last move
     * @param type color of the last move
     * @return boolean true if the game is over
     */
    public boolean gameOver(Position pos, PieceType type) {
        if (checkRow(pos.row(), type) || checkCol(pos.col(), type)
                || checkLongDiagonal(pos, type) || checkShortDiagonal(pos, type)) {
            System.out.println(this);

            return true;
        }
        return false;
    }


    /**
     * Check whether the location is within the board bounds.
     * @param i row
     * @param j column
     * @return boolean true if the location is within the board bounds
     */
    public boolean isOnBoard(int i, int j) {
        return i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE;
    }

    /**
     * Checks if a column has 3 occurrences of the same color.
     * @param col column to be checked
     * @param type color of the piece
     * @return boolean true if the column has 3 occurrences of the same color
     */
    public boolean checkCol(int col, PieceType type) {
        int count = 0;
        logger.debug("Checking column {}", col);
        for (var row = 0; row < BOARD_SIZE; row++) {
            if (board[row][col].get().getType() == type) {
                count++;
                logger.debug("Column {} has {} occurrences of {}", col, count, type);
            } else
                count = 0;
            if (count == 3) {
                logger.warn("Column {} has 3 occurrences of {}", col, type);
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if a row has 3 occurrences of the same color.
     * @param row row to be checked
     * @param type color of the piece
     * @return boolean true if the row has 3 occurrences of the same color
     */
    public boolean checkRow(int row, PieceType type) {

        int count = 0;
        logger.debug("Checking row {}, {}", row, count);
        for (var col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col].get().getType().equals(type)) {
                count++;
            } else count = 0;
            if (count == 3) {
                logger.warn("Row {} has 3 occurrences of {}", row, type);
                return true;
            }
        }
        return false;
    }


    /**
     * Checks far Positions from the Position for diagonal.
     * @param position the Position to check
     * @param type color of the Piece
     * @return boolean true if the diagonal has 3 occurrences of the same color
     */
    public boolean checkLongDiagonal(Position position, PieceType type) {
        for (Direction dir : PawnDirection.values()) {
            Position tmp = position.moveTo(dir);
            if (isOnBoard(tmp.row(), tmp.col())) {
                if (board[tmp.row()][tmp.col()].get().getType().equals(type)) {
                    if (isOnBoard(tmp.moveTo(dir).row(), tmp.moveTo(dir).col())) {
                        Position tmp2 = tmp.moveTo(dir);
                        if (board[tmp2.row()][tmp2.col()].get().getType().equals(type)) {
                            logger.warn("Long diagonal has 3 occurrences of {}", type);
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks near Positions from the Position for diagonal.
     * @param pos the 1st Position to check
     * @param pos1 the 2nd Position to check
     * @param type color of the Piece
     * @return boolean true if the diagonal has 3 occurrences of the same color
     */
    public boolean checkCell(Position pos, Position pos1, PieceType type) {
        if (isOnBoard(pos.row(), pos.col()) &&
                board[pos.row()][pos.col()].get().getType().equals(type)) {
                if ((isOnBoard(pos1.row(), pos1.col())) &&
                        (board[pos1.row()][pos1.col()].get().getType().equals(type)) ){
                        logger.warn("Cell has 3 occurrences of {}", type);
                        return true;
                    } else {
                        return false;
                    }
                }
        return false;
    }


    /**
     * Checks if a diagonal has 3 occurrences of the same color.
     * @param pos the Position to check
     * @param type color of the Piece
     * @return boolean true if the diagonal has 3 occurrences of the same color
     */
    public boolean checkShortDiagonal(Position pos, PieceType type) {
        Position position1 = pos.moveTo(PawnDirection.UP_LEFT);
        Position position2 = pos.moveTo(PawnDirection.DOWN_RIGHT);
        Position position3 = pos.moveTo(PawnDirection.UP_RIGHT);
        Position position4 = pos.moveTo(PawnDirection.DOWN_LEFT);
        if (checkCell(position1, position2, type) || checkCell(position3, position4, type)) {
            logger.info("Diagonal has 3 occurrences of the same color");
            return true;
        }
        return false;
    }

    /**
     * Returns the color of the current player.
     * @return the Color of the player who has the turn
     */
    public PieceType CurrentPlayer(){
        return moveCount % 2 == 0? PieceType.RED : PieceType.BLUE;
    }


    /**
     * Returns the String representation of the board.
     * @return String representation of the board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j].get().getType().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
//
//    public static void main(String[] args) {
////        GameModel game = new GameModel();
////        game.click(0, 0);
////        game.click(3, 4);
////        System.out.println(game.squareProperty(0, 0));
////        game.click(1, 0);
////        game.click(4, 1);
////        game.click(3, 0);
////        game.click(1, 2);
////        game.click(3, 3);
////        game.click(2, 3);
////        System.out.println(game);
////        System.out.println(game.CurrentPlayer());
//    }

}

