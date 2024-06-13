package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.IOException;
import java.sql.SQLException;

public class SignupView {
    @FXML
    private TextField username;
    @FXML
    private TextField name;
    @FXML
    private PasswordField password;
    @FXML
    private Label usernameNotUniqueLBL;

    public void signupUser(ActionEvent actionEvent) throws SQLException, IOException {
        if (DatabaseManager.usernameIsUnique(this.username.getText())){
            usernameNotUniqueLBL.setVisible(false);
            String username = this.username.getText();
            String name = this.name.getText();
            if (username != null && !username.isEmpty() && name != null && !name.isEmpty() && this.password.getText() != null && !this.password.getText().isEmpty()) {
                String password = DigestUtils.sha256Hex(this.password.getText());
                DatabaseManager.SignUp(name, username, password);

                FXMLLoader fxmlLoader = new FXMLLoader(HangmanApp.class.getResource("first-view.fxml"));
                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Hangman");
                stage.setScene(scene);
                stage.show();
            }
        }
        else {
            usernameNotUniqueLBL.setVisible(true);
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
