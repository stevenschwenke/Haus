import java.util.HashMap;
import java.util.Map;

/**
 * Delivery of data for the questions.
 *
 * Created by steven on 18.09.14.
 */
public class QuestionStore {

    private Map<Long, QuestionWithAnswer> questionsAnswersMap = new HashMap<>();

    private long counter = 0L;

    public QuestionStore() {
        questionsAnswersMap.put(0L, new QuestionWithAnswer("Question 1", "Answer 1"));
        questionsAnswersMap.put(1L, new QuestionWithAnswer("Question 2", "Answer 2"));
    }

    public QuestionWithAnswer getNewQeQuestionWithAnswer() {
        if(counter < questionsAnswersMap.size()) {
            QuestionWithAnswer nextQuestionWithAnswer = questionsAnswersMap.get(counter);
            counter++;
            return nextQuestionWithAnswer;
        } else {
            counter = 0;
            QuestionWithAnswer nextQuestionWithAnswer = questionsAnswersMap.get(counter);
            counter++;
            return nextQuestionWithAnswer;
        }
    }
}
