package hangman;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HangmanController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}