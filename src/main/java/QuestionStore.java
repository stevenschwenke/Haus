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

    private Jongo jongo;

    /**
     * Iterator to scroll through the questions
     */
    private Iterator<QuestionWithAnswer> iterator;

    public QuestionStore() {

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
