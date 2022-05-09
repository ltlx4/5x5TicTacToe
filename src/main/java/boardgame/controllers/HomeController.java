package boardgame.controllers;

import boardgame.models.Piece;
import boardgame.models.PieceType;
import boardgame.models.Player;
import boardgame.models.Position;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * HomeController class is the controller for the home screen.
 * It handles the Players input and starts the game.
 */
public class HomeController {
    @FXML
    TextField playerOne;
    @FXML
    TextField playerTwo;
    @FXML
    Button startButton;
    @FXML
    Button historyButton;
    @FXML
    Button quitButton;
    @FXML
    Label missingField;
    @FXML
    FXMLLoader loader = new FXMLLoader();

    private static final Logger logger = LogManager.getLogger();



    @FXML
    public void handlePlay(ActionEvent event) throws IOException {
        if (playerOne == null || playerTwo == null || playerOne.getText().isEmpty() || playerTwo.getText().isEmpty()) {
            missingField.setText("Please fill in both fields");
            logger.error("Missing fields");
        } else {
            String redPlayer = playerOne.getText();
            String bluePlayer = playerTwo.getText();
            loader.setLocation(getClass().getResource("/fxml/game.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = loader.load();
            GameController gameController = loader.getController();
            gameController.displayNames(redPlayer, bluePlayer);
            setPlayers(redPlayer, bluePlayer);
            stage.setScene(new Scene(root));
            logger.info("Game started!");
        }
    }







    @FXML
    public void handleQuit(ActionEvent event) {
        Platform.exit();
        logger.info("Game quit");
    }

    @FXML
    public void handleHistory(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/fxml/history.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = loader.load();
        stage.setScene(new Scene(root));

    }

}
