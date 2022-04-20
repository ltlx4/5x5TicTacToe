package boardgame.controllers;

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

import java.io.IOException;

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

    @FXML
    public void handlePlay(ActionEvent event) throws IOException {
        if (playerOne == null || playerTwo == null || playerOne.getText().isEmpty() || playerTwo.getText().isEmpty()) {
            missingField.setText("Please fill in both fields");
        } else {
            loader.setLocation(getClass().getResource("/fxml/game.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        }
    }

    @FXML
    public void handleQuit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void handleHistory(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/fxml/history.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = loader.load();
        stage.setScene(new Scene(root));

    }

}
