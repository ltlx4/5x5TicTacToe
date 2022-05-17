package boardgame.controllers;

import util.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * HistoryController class for the history scene.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryController {


    @FXML
    private final FXMLLoader loader = new FXMLLoader();

    @FXML
    private Button backButton;

    @FXML
    private TableView<Result> tableView;

    @FXML
    private TableColumn<Result, String> code;
    @FXML
    private TableColumn<Result, String> redName;

    @FXML
    private TableColumn<Result, String> blueName;

    @FXML
    private TableColumn<Result, String> winnerName;

    @FXML
    private TableColumn<Result, Long> movesCount;
    @FXML
    private TableColumn<Result, LocalDate> dateTime;


    @FXML
    public void initialize() throws IOException {
        redName.setCellValueFactory(new PropertyValueFactory<>("redName"));
        blueName.setCellValueFactory(new PropertyValueFactory<>("blueName"));
        winnerName.setCellValueFactory(new PropertyValueFactory<>("winnerName"));
        movesCount.setCellValueFactory(new PropertyValueFactory<>("moveCount"));
        dateTime.setCellValueFactory(new PropertyValueFactory<>("date"));
        List<Result> games = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .readValue(HistoryController.class.getResourceAsStream("/games.json"), new TypeReference<>() {} );

        ObservableList<Result> observableList = FXCollections.observableArrayList();
        observableList.addAll(games);
        tableView.setItems(observableList);


    }

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
