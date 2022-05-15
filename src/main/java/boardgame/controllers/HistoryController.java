package boardgame.controllers;

import boardgame.models.Position;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * HistoryController class for the history scene.
 */
public class HistoryController {

    @FXML
    FXMLLoader loader = new FXMLLoader();

    @FXML
    Button backButton;


    /**
     * Quits the game.
     * @param event the event that triggered the method.
     */
    @FXML
    public void handleQuit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Goes back to the main menu.
     * @param event the event that triggered the method.
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/fxml/home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = loader.load();
        stage.setScene(new Scene(root));

    }

}
