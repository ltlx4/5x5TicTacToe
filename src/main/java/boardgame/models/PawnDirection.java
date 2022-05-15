package boardgame.models;


/**
 * Enum for the direction of a pawn.
 */
public enum PawnDirection implements Direction {

    /**
     * The direction of a pawn is up left.
     */
    UP_LEFT(-1, -1),
    /**
     * The direction of a pawn is up right.
     */
    UP_RIGHT(-1, 1),
    /**
     * The direction of a pawn is down right.
     */
    DOWN_RIGHT(1, 1),
    /**
     * The direction of a pawn is down left.
     */
    DOWN_LEFT(1, -1);

    private final int rowChange;
    private final int colChange;

    /**
     * Constructor for the enum.
     * @param rowChange the change in row
     * @param colChange the change in column
     */
    PawnDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * Returns the change in row.
     * @return the change in column
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * Return the change in column.
     * @return the change in row
     */
    public int getColChange() {
        return colChange;
    }


}
