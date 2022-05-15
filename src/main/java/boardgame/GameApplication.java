package boardgame;

import java.io.IOException;

import boardgame.controllers.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The GameApplication class.
 */
public class GameApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("/fxml/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("5x5 R&B Stones");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
