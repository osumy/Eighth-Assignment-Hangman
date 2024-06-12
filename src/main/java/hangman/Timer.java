package hangman;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Timer implements Runnable {
    @FXML
    private Label timerLbl;
    private LocalTime startPoint;
    public Timer(Label timerLbl){
        this.timerLbl = timerLbl;
        startPoint = LocalTime.now();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                Platform.runLater(() -> timerLbl.setText(Long.toString(Duration.between(startPoint, LocalTime.now()).get(ChronoUnit.SECONDS))));
            }
        }
        catch (InterruptedException e) {
            System.out.println("Timer Finished...");
        }
    }
}

