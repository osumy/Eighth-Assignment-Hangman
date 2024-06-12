package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.SQLException;

public class LoginView {
    @FXML
    private Button loginBtn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button returnBtn;
    @FXML
    private Label user404;
    @FXML
    private Label pass404;


    public void loginUser(ActionEvent actionEvent) throws SQLException, IOException {
        String username = this.username.getText();
        String password = this.password.getText();

        if (!username.isEmpty() && !password.isEmpty()){
            password = DigestUtils.sha256Hex(password);
            if (!DatabaseManager.usernameIsUnique(username)){
                user404.setVisible(false);
                if (DatabaseManager.passMatch(password)){
                    pass404.setVisible(false);
                    HangmanApp.user = DatabaseManager.login(username);

                    FXMLLoader fxmlLoader = new FXMLLoader(HangmanApp.class.getResource("main-view.fxml"));
                    Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Hangman");
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    pass404.setVisible(true);
                }
            }
            else {
                user404.setVisible(true);
            }
        }
    }

    public void returnMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HangmanApp.class.getResource("first-view.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hangman");
        stage.setScene(scene);
        stage.show();
    }
}
