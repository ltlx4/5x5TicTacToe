package boardgame.models;


/**
 * Position class represents a position on the board.
 * @param row row of the position
 * @param col column of the position
 */
public record Position(int row, int col) {


    /**
     * Returns the new position after moving in the given direction.
     * @param direction the direction
     * @return Position the new position
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}