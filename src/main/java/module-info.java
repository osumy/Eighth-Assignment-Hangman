module hangman {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires org.apache.commons.codec;

    opens hangman to javafx.fxml;
    exports hangman;
}