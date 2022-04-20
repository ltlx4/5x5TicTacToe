package boardgame.controllers;

import boardgame.models.Direction;
import boardgame.models.PawnDirection;
import boardgame.models.Position;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class GameController {

    @FXML
    private GridPane board;

    @FXML
    public void quitHandler(ActionEvent event) {
        Platform.exit();
    }


    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }


    @FXML
    private void initialize() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        System.out.printf("Click on square (%d,%d)%n", row, col);
        Position position = new Position(row, col);
        var squar = getSquare(position);
        squar.getStyleClass().add("selected");


        var coin = (Circle) square.getChildren().get(0);
        System.out.println(coin.getFill());
        var stepColor = nextColor((Color) coin.getFill());
        coin.setFill(stepColor);
        gameFinished(position, stepColor);

//        System.out.println(checkCol(col, (Color) coin.getFill()));
    }

    /**
     * Checks if a column has 3 occurrences of the same color
     * @param col
     * @return
     */
    private boolean checkCol(int col, Color color) {
        int count = 0;
        for (int row = 0; row < board.getRowCount(); row++) {
            var square = getSquare(new Position(row, col));
            var piece = (Circle) square.getChildren().get(0);
            if (piece.getFill() == color) {
                count++;
            }else
                count = 0;
            if (count == 3) {
                return true;
            }
        }
        return count == 3;
    }

    private boolean checkRow(int row, Color color) {
        int count = 0;
        for (int col = 0; col < board.getColumnCount(); col++) {
            var square = getSquare(new Position(row, col));
            var piece = (Circle) square.getChildren().get(0);
            if (piece.getFill() == color) {
                count++;
            }else
                count = 0;
            if (count == 3) {
                return true;
            }
        }
        return count == 3;
    }

    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < 5
                && 0 <= position.col() && position.col() < 5;
    }

    private boolean checkLongDiagonal(Position position, Color color){
        for (Direction d : PawnDirection.values()) {
            Position temp = position.moveTo(d);
            System.out.println(temp);
            if (isOnBoard(temp)) {
                var square = getSquare(temp);
                var piece = (Circle) square.getChildren().get(0);
                if (piece.getFill() == color) {
                    if (isOnBoard(temp.moveTo(d))) {
                        var square3 = getSquare(temp.moveTo(d));
                        var piece3 = (Circle) square3.getChildren().get(0);
                        if (piece3.getFill() == color) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    ;
                }
            }
        }
        return false;
    }

    private boolean checkShortDiagonal(Position position, Color color){
        Position position1 = position.moveTo(PawnDirection.UP_LEFT);
        Position position2 = position.moveTo(PawnDirection.DOWN_RIGHT);
        Position position3 = position.moveTo(PawnDirection.UP_RIGHT);
        Position position4 = position.moveTo(PawnDirection.DOWN_LEFT);
        if (checkShortCells(color, position1, position2)) {
            return true;
        }
        else if (checkShortCells(color, position3, position4)) {
            return true;
        }
        return false;
    }

    private boolean checkShortCells(Color color, Position position1, Position position2) {
        if (isOnBoard(position1) && isOnBoard(position2)) {
            var square1 = getSquare(position1);
            var square2 = getSquare(position2);
            var piece1 = (Circle) square1.getChildren().get(0);
            var piece2 = (Circle) square2.getChildren().get(0);
            if (piece1.getFill() == color && piece2.getFill() == color) {
                return true;
            }
        }
        return false;
    }

    private Boolean gameFinished(Position position, Color color) {
        if (checkCol(position.col(), color) || checkRow(position.row(), color)
                || checkLongDiagonal(position, color) || checkShortDiagonal(position, color)) {
            System.out.println("Game finished");
            System.out.println();
            return true;
        }
        return false;
    }

    private Color nextColor(Color color) {
        if (color == Color.TRANSPARENT) {
            return Color.RED;
        }
        if (color == Color.RED) {
            return Color.BLUE;
        }
        return Color.TRANSPARENT;
    }


    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);
        piece.setFill(Color.TRANSPARENT);
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }


}
