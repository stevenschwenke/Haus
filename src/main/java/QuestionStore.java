import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Delivery of data for the questions.
 * <p>
 * Created by steven on 18.09.14.
 */
public class QuestionStore {

    private Map<Long, QuestionWithAnswer> questionsAnswersMap = new HashMap<>();

    private long counter = 0L;

    private Jongo jongo;
    private Iterator<QuestionWithAnswer> iterator;

    public QuestionStore() {
        //questionsAnswersMap.put(0L, new QuestionWithAnswer("Question 1", "Answer 1"));
        //questionsAnswersMap.put(1L, new QuestionWithAnswer("Question 2", "Answer 2"));

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("mydb");
            jongo = new Jongo(db);
            MongoCollection questions = jongo.getCollection("stevensCollection");

            MongoCursor<QuestionWithAnswer> all = questions.find("{category: 'dump questions'}").as(QuestionWithAnswer.class);
            iterator = all.iterator();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

//    public QuestionWithAnswer getNewQuestionWithAnswer() {
//        if(counter < questionsAnswersMap.size()) {
//            QuestionWithAnswer nextQuestionWithAnswer = questionsAnswersMap.get(counter);
//            counter++;
//            return nextQuestionWithAnswer;
//        } else {
//            counter = 0;
//            QuestionWithAnswer nextQuestionWithAnswer = questionsAnswersMap.get(counter);
//            counter++;
//            return nextQuestionWithAnswer;
//        }
//    }

    public QuestionWithAnswer getNewQuestionWithAnswer() {
        if (iterator.hasNext()) {
            QuestionWithAnswer next = iterator.next();
            return next;
        } else {
            MongoCollection questions = jongo.getCollection("stevensCollection");
            MongoCursor<QuestionWithAnswer> all = questions.find("{category: 'dump questions'}").as(QuestionWithAnswer.class);
            iterator = all.iterator();
            QuestionWithAnswer next = iterator.next();
            return next;
        }
    }
}
