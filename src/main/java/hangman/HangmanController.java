package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HangmanController {
    public static String word;
    @FXML
    private Label timeLbl;
    public static int time;
    Thread thread;

    @FXML
    public void initialize(){
        word = "apple";

        Timer timer = new Timer(timeLbl);
        thread = new Thread(timer);
        thread.start();
    }

    public void returnMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HangmanApp.class.getResource("main-view.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hangman");
        stage.setScene(scene);
        stage.show();
        thread.interrupt();
    }
}
