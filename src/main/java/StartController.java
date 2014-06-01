import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StartController {

    @FXML
    private Label date;

    @FXML
    void initialize() {
        startClock();
    }

    private void startClock() {

        final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss, dd.MM.yyyy");

        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // TODO This is kind of ugly ... is a new calendar instance created every second?
                Calendar cal = Calendar.getInstance();
                date.textProperty().set(dateFormat.format(cal.getTime()));
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.playFromStart();
    }

    @FXML
    public void close() {
        System.exit(0);
    }
}
