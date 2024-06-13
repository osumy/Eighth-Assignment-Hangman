package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HangmanController {
    public static List<Character> word = new ArrayList<>();
    String gameWord;
    @FXML
    private Label timeLbl;
    Thread thread;
    public int HP;

    @FXML
    private Circle head;
    @FXML
    private Line body;
    @FXML
    private Line lHand;
    @FXML
    private Line rHand;
    @FXML
    private Line lFoot;
    @FXML
    private Line rFoot;
    @FXML
    private Pane gameOver;
    @FXML
    private HBox letterBox;
    private int letterWins = 0;


    @FXML
    public void initialize() throws SQLException {
        gameWord = DatabaseManager.randomWord();
        System.out.println(gameWord);
        for (int i = 0; i < gameWord.length(); i++){
            word.add(i, gameWord.charAt(i));
        }

        Timer timer = new Timer(timeLbl);
        thread = new Thread(timer);
        thread.start();

        HP = 6;

        head.setVisible(false);
        body.setVisible(false);
        lHand.setVisible(false);
        rHand.setVisible(false);
        lFoot.setVisible(false);
        rFoot.setVisible(false);
        gameOver.setVisible(false);

        createLetterBox();
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

    public void wrongGuess() throws InterruptedException, IOException, SQLException {
        HP--;
        switch (HP){
            case 5:
                head.setVisible(true);
            break;
            case 4:
                body.setVisible(true);
            break;
            case 3:
                lHand.setVisible(true);
            break;
            case 2:
                rHand.setVisible(true);
            break;
            case 1:
                lFoot.setVisible(true);
            break;
            case 0:
                thread.interrupt();
                rFoot.setVisible(true);
                //Thread.sleep(500);
                //gameOver.setVisible(true);
                DatabaseManager.saveGame(HangmanApp.user.getUsername(), gameWord, 6, Integer.parseInt(timeLbl.getText()), 0);
                //Thread.sleep(1500);
                FXMLLoader fxmlLoader = new FXMLLoader(HangmanApp.class.getResource("main-view.fxml"));
                Stage stage = (Stage) (timeLbl.getScene().getWindow());
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Hangman");
                stage.setScene(scene);
                stage.show();
            break;
        }


    }

    public void createLetterBox(){
        for (int i = 0; i < word.size(); i++){
            Label lbl = new Label("_");
            lbl.setStyle("-fx-font-size: 72");
            letterBox.getChildren().add(lbl);
        }
    }

    public void guess(ActionEvent actionEvent) throws IOException, InterruptedException, SQLException {
        ((Button)actionEvent.getSource()).setVisible(false);
        char ch = ((Button)actionEvent.getSource()).getText().charAt(0);
        int x = 0;
        for (int i = 0; i < word.size(); i++){
            if (word.get(i).toString().toLowerCase().equals(Character.toString(ch).toLowerCase())) {
                ((Label)letterBox.getChildren().get(i)).setText(Character.toString(ch));
                letterWins++;
                x++;
            }
        }
        if (x == 0){
            wrongGuess();
        }
        if (letterWins == word.size()){
            thread.interrupt();
            rFoot.setVisible(true);
            //Thread.sleep(500);
            //gameOver.setVisible(true);
            DatabaseManager.saveGame(HangmanApp.user.getUsername(), gameWord, 6-HP, Integer.parseInt(timeLbl.getText()), 1);
            //Thread.sleep(1500);
            FXMLLoader fxmlLoader = new FXMLLoader(HangmanApp.class.getResource("main-view.fxml"));
            Stage stage = (Stage) (timeLbl.getScene().getWindow());
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hangman");
            stage.setScene(scene);
            stage.show();
        }
    }
}
