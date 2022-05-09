package boardgame.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Player {
    private final String name;

    private final ObjectProperty<Piece> piece = new SimpleObjectProperty<>();

    public Player(String name, Piece piece) {
        this.name = name;
        this.piece.set(piece);
    }

    public String getName() {
        return name;
    }

    public Piece getPiece() {
        return piece.get();
    }

    public ObjectProperty<Piece> pieceProperty() {
        return piece;
    }

    public String toString() {
        return name;
    }

}
