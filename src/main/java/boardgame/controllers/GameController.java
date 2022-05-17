package boardgame.controllers;

import boardgame.Main;
import boardgame.models.*;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.JsonHelper;
import util.result.Result;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


/**
 * The GameController class is the controller for the game.
 * It handles the game logic and the GUI.
 *
 */
public class GameController {

    @FXML
    private GridPane board;
    @FXML
    private Button redLabel;
    @FXML
    private Button blueLabel;


    private GameModel model;
    private static final Logger logger = LogManager.getLogger();

    /**
     * Quits the game.
     * @param event event that triggered the method
     */
    @FXML
    public void quitHandler(ActionEvent event) {
        Platform.exit();
    }


    /**
     * Initializes the game by creating the stones for the board.
     */
    @FXML
    private void initialize() {
        model = new GameModel();
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
        redLabel.setStyle("-fx-background-color: #c51f1f");
        logger.warn("Board initialized successfully");
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        logger.info("Click on square ({},{})", row, col);
        square.getStyleClass().add("selected");
        model.click(row, col);
        if (model.gameOver(new Position(row, col), model.squareProperty(row, col).get().getType())) {
            try {
                gameFinished(nextColor());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (model.getMoveCount() % 2 ==0 ) {
                redLabel.setStyle("-fx-background-color: #c51f1f");
                blueLabel.setStyle("-fx-background-color: #27374d");
            } else {
                blueLabel.setStyle("-fx-background-color: #3369ce");
                redLabel.setStyle("-fx-background-color: #27374d");
            }
        }

    }



    /**
     * Checks if the game is over and disables the board if it is.
     * @param color the color of the last stone
     */
    private void gameFinished(Color color) throws IOException {
            logger.info("Game over");
            board.setDisable(true);
            String winner;
            if (color == Color.BLUE) {
                winner = redLabel.getText();
                redLabel.setStyle("-fx-background-color: #ffd802");
                logger.info("{} wins", redLabel.getText());

            }
            else {
                winner = blueLabel.getText();
                blueLabel.setStyle("-fx-background-color: #ffd802;");
                logger.info("{} wins", blueLabel.getText());
            }
            var faker = new Faker();

            var result = Result.builder()
                    .redName(redLabel.getText())
                    .blueName(blueLabel.getText())
                    .winnerName(winner)
                    .moveCount(model.getMoveCount())
                    .date(LocalDate.now()).build();
            saveGameToJson(result);
    }


    private void saveGameToJson(Result result){
        JsonHelper.write(result);
    }


    /**
     * Returns the next color of the stone.
     * @return Color the next color of the stone
     */
    private Color nextColor() {
        if (model.getMoveCount() % 2 == 0) {
            return Color.RED;
        }
        else {
            return Color.BLUE;
        }
    }


    /**
     * Sets the name of the players.
     * @param name1 String of the first player
     * @param name2 String of the second player
     */
    public void displayNames(String name1, String name2) {
        redLabel.setText(name1);
        blueLabel.setText(name2);
    }



    /**
     * Returns stack pane shaped like a square with a {@link Circle} inside.
     * @param i row of the square
     * @param j column of the square
     * @return {@link StackPane} shaped like a square with a {@link Circle} inside
     */
    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.setOnMouseClicked(this::handleMouseClick);
        square.getStyleClass().add("square");
        var piece = new Circle(50);

        piece.fillProperty().bind(
                new ObjectBinding<>() {
                    {
                        super.bind(model.squareProperty(i,j));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.squareProperty(i,j).get().getType()) {
                            case EMPTY -> Color.TRANSPARENT;
                            case RED -> Color.RED;
                            case BLUE -> Color.BLUE;
                        };
                    }
                }
        );

        square.getChildren().add(piece);
        return square;
    }


}
