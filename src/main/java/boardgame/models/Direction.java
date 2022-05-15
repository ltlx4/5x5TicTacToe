package boardgame.models;

/**
 * Enum for the direction of the player.
 */
public interface Direction {

    /**
     * Returns the row change.
     * @return the row change
     */
    int getRowChange();

    /**
     * Returns the column change.
     * @return the column change
     */
    int getColChange();

}
