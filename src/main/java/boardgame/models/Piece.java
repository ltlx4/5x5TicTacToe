package boardgame.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The Piece class is a model class for a piece.
 * contains the piece's type, and position.
 */
public class Piece {

    private final PieceType type;
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    /**
     * Constructor for a piece.
     * @param type the piece's type
     * @param position the piece's position
     */
    public Piece(PieceType type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    /**
     * @return the piece's type
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Returns the string representation of the piece.
     * @return the string representation of the piece
     */
    public String toString() {
        return type.toString() + position.get().toString();
    }


}
