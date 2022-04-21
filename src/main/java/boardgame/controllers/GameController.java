package boardgame.controllers;

import boardgame.models.Direction;
import boardgame.models.PawnDirection;
import boardgame.models.Position;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The GameController class is the controller for the game.
 * It handles the game logic and the GUI.
 *
 */
public class GameController {

    @FXML
    private GridPane board;

    @FXML
    Button redLabel;

    @FXML
    Button blueLabel;

    private static final Logger logger = LogManager.getLogger();

    @FXML
    public void quitHandler(ActionEvent event) {
        Platform.exit();
    }


    /**
     * Returns square in the board that corresponds to the position
     * @param position position of the square
     * @return square in the board that corresponds to the position
     */
    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }


    /**
     * Initializes the game by creating the stones for the board.
     */
    @FXML
    private void initialize() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
        logger.warn("Board initialized successfully");
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        logger.info("Click on square ({},{})", row, col);
        square.getStyleClass().add("selected");
        var coin = (Circle) square.getChildren().get(0);
        var stepColor = nextColor((Color) coin.getFill());
        coin.setFill(stepColor);
        gameFinished(new Position(row, col), stepColor);
    }

    /**
     * Checks if a column has 3 occurrences of the same color
     * @param col column to check
     * @param color color of the piece
     * @return boolean true if the column has 3 occurrences of the same color
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
                logger.info("Column {} has 3 occurrences of the same color", col);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a row has 3 occurrences of the same color
     * @param row row to check
     * @param color color of the piece
     * @return boolean true if the row has 3 occurrences of the same color
     */
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
                logger.info("Row {} has 3 occurrences of the same color", row);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the Position is in the board
     * @param position position to check
     * @return boolean true if the position is in the board
     */
    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < 5
                && 0 <= position.col() && position.col() < 5;
    }

    /**
     * Checks far Positions from the Position for diagonal
     * @param position the Position to check
     * @param color color of the coin
     * @return boolean true if the diagonal has 3 occurrences of the same color
     */
    private boolean checkLongDiagonal(Position position, Color color){
        for (Direction d : PawnDirection.values()) {
            Position temp = position.moveTo(d);
            if (isOnBoard(temp)) {
                var square = getSquare(temp);
                var piece = (Circle) square.getChildren().get(0);
                if (piece.getFill() == color) {
                    if (isOnBoard(temp.moveTo(d))) {
                        var square3 = getSquare(temp.moveTo(d));
                        var piece3 = (Circle) square3.getChildren().get(0);
                        if (piece3.getFill() == color) {
                            logger.info("Diagonal has 3 occurrences of the same color");
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks near Positions from the Position for diagonal
     * @param position the Position to check
     * @param color color of the coin
     * @return boolean true if the diagonal has 3 occurrences of the same color
     */
    private boolean checkShortDiagonal(Position position, Color color){
        Position position1 = position.moveTo(PawnDirection.UP_LEFT);
        Position position2 = position.moveTo(PawnDirection.DOWN_RIGHT);
        Position position3 = position.moveTo(PawnDirection.UP_RIGHT);
        Position position4 = position.moveTo(PawnDirection.DOWN_LEFT);
        if (checkShortCells(color, position1, position2)) {
            logger.info("Diagonal has 3 occurrences of the same color");
            return true;
        }
        else if (checkShortCells(color, position3, position4)) {
            logger.info("Diagonal has 3 occurrences of the same color");
            return true;
        }
        return false;
    }

    /**
     * Checks if the neighboring cells the same color
     * @param color color of the stone
     * @param position1 first position to check
     * @param position2 second position to check
     * @return boolean true if the cells are the same color
     */
    private boolean checkShortCells(Color color, Position position1, Position position2) {
        if (isOnBoard(position1) && isOnBoard(position2)) {
            var square1 = getSquare(position1);
            var square2 = getSquare(position2);
            var piece1 = (Circle) square1.getChildren().get(0);
            var piece2 = (Circle) square2.getChildren().get(0);
            if (piece1.getFill() == color && piece2.getFill() == color) {
                logger.info("Diagonal has 3 occurrences of the same color");
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the game is over and disables the board if it is.
     * @param position the last position entered to check
     * @param color the color of the last stone
     */
    private void gameFinished(Position position, Color color) {
        if (checkCol(position.col(), color) || checkRow(position.row(), color)
                || checkLongDiagonal(position, color) || checkShortDiagonal(position, color)) {
            logger.info("Game over");
            board.setDisable(true);
            if (color == Color.RED) {
                redLabel.setStyle("-fx-background-color: #ffd802;");
                logger.info("{} wins", redLabel.getText());

            }
            else {
                blueLabel.setStyle("-fx-background-color: #ffd802;");
                logger.info("{} wins", blueLabel.getText());
            }
        }
    }


    /**
     * returns the next color of the stone
     * @param color the color of the last stone
     * @return Color the next color of the stone
     */
    private Color nextColor(Color color) {
        if (color == Color.TRANSPARENT) {
            logger.debug("Changing color to red");
            return Color.RED;
        }
        if (color == Color.RED) {
            logger.debug("Changing color to blue");
            return Color.BLUE;
        }
        logger.debug("Changing color to transparent");
        return Color.TRANSPARENT;
    }


    /**
     * Sets the name of the players
     * @param name1 String of the first player
     * @param name2 String of the second player
     */
    public void displayNames(String name1, String name2) {
        redLabel.setText(name1);
        blueLabel.setText(name2);
    }

    /**
     * Returns stack pane shaped like a square with a circle inside
     * @return StackPane shaped like a square with a circle inside
     */
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
