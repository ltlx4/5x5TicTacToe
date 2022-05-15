package boardgame.models;

/**
 * Enum for the different types of pieces.
 */
public enum PieceType {
    /**
     * Empty type of piece.
     */
    EMPTY,
    /**
     * Red type of piece.
     */
    RED,
    /**
     * Blue type of piece.
     */
    BLUE;


    /**
     * Returns boolean if the piece is equal to the given piece.
     * @param other the piece to compare to.
     * @return true if the pieces are equal, false otherwise.
     */
    public boolean equals(PieceType other) {
        return this == other;
    }
}
