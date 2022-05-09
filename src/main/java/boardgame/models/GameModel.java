package boardgame.models;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class GameModel {

    public static int BOARD_SIZE = 5;

    private ReadOnlyObjectWrapper<Piece>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);



    private ReadOnlyObjectWrapper<Player> redPlayer = new ReadOnlyObjectWrapper<>();
    private ReadOnlyObjectWrapper<Player> bluePlayer = new ReadOnlyObjectWrapper<>();


    public GameModel(Player redPlayer, Player bluePlayer) {
        this.redPlayer.set(redPlayer);
        this.bluePlayer.set(bluePlayer);
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                var pos = new Position(i, j);
                board[i][j] = new ReadOnlyObjectWrapper<>(new Piece(PieceType.EMPTY, pos));
            }
        }
    }


    public ReadOnlyObjectProperty<Piece> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public Piece getPiece(int i, int j) {
        return board[i][j].get();
    }

    public void click(int i, int j) {
        board[i][j].set(
                switch (board[i][j].get().getType()) {
                    case EMPTY -> new Piece(PieceType.RED, new Position(i, j));
                    case RED -> new Piece(PieceType.BLUE, new Position(i, j));
                    case BLUE -> new Piece(PieceType.EMPTY, new Position(i, j));

                }
        );
    }

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

    public static void main(String[] args) {
    }

}

