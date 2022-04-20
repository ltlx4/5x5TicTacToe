package boardgame.models;

import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.OptionalInt;

public class GameModel {

    public static int BOARD_SIZE = 5;
    private final Piece[] pieces;

    public GameModel() {
        pieces = new Piece[BOARD_SIZE * BOARD_SIZE];
    }





    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }
}
