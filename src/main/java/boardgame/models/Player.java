package boardgame.models;


/**
 * Player class.
 */
public enum Player {
    /**
     * Player 1.
     */
    RED,
    /**
     * Player 2.
     */
    BLUE;


    /**
     * @return PieceType representing the current player color.
     */
    public PieceType getPieceType() {
        return switch (this) {
            case RED -> PieceType.RED;
            case BLUE -> PieceType.BLUE;
        };
    }
}