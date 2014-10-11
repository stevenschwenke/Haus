import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StartController {

    @FXML
    private Label date;

    @FXML
    private TextArea text;

    private QuestionStore questionStore = new QuestionStore();

    private QuestionWithAnswer currentQuestionWithAnswer;

    @FXML
    void initialize() {
        startClock();
        ShowNextQuestionOrNextAnswer();
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
    private void ShowNextQuestionOrNextAnswer() {

        // first execution run
        if (currentQuestionWithAnswer == null) {
            currentQuestionWithAnswer = questionStore.getNewQuestionWithAnswer();
            text.textProperty().set(currentQuestionWithAnswer.getQuestion());
        } else {

            if (text.textProperty().get().equals(currentQuestionWithAnswer.getQuestion())) {
                text.textProperty().set(currentQuestionWithAnswer.getAnswer());
            } else {
                currentQuestionWithAnswer = questionStore.getNewQuestionWithAnswer();
                text.textProperty().set(currentQuestionWithAnswer.getQuestion());
            }
        }
    }

    @FXML
    public void close() {
        System.exit(0);
    }
}
