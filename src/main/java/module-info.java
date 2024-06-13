module hangman {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens hangman to javafx.fxml;
    exports hangman;
}