import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoTimeoutException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.net.UnknownHostException;
import java.util.Iterator;

/**
 * Delivery of data for the questions.
 * <p>
 * Created by steven on 18.09.14.
 */
public class QuestionStore {

    private Log log = LogFactory.getLog(QuestionStore.class);

    /**
     * Host where the Mongo DB can be reached
     */
    public static final String MONGODB_HOST = "localhost";

    /**
     * Port where the Mongo DB can be reached
     */
    public static final int MONGODB_PORT = 27017;

    /**
     * Object mapper for MongoDB.
     */
    private Jongo jongo;

    /**
     * Iterator to scroll through the questions
     */
    private Iterator<QuestionWithAnswer> qestionIterator;

    public QuestionStore() {
        qestionIterator = setupConnectionToDatabaseAndQestionIterator();
    }

    /**
     * Connects to the database and setup an iterator with which the questions can be read.
     *
     * @return iterator for the questions
     */
    private Iterator<QuestionWithAnswer> setupConnectionToDatabaseAndQestionIterator() {
        log.info("Connecting to database at "+MONGODB_HOST+":"+MONGODB_PORT);
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(MONGODB_HOST, MONGODB_PORT);
            DB db = mongoClient.getDB("mydb");
            jongo = new Jongo(db);
            MongoCollection questions = jongo.getCollection("questions");

            MongoCursor<QuestionWithAnswer> all = questions.find("{category: 'dump questions'}").as(QuestionWithAnswer.class);
            return all.iterator();
        } catch (UnknownHostException e) {
            log.error("Unknown Host: " + MONGODB_HOST, e);
            throw new RuntimeException(e);
        }
    }

    public QuestionWithAnswer getNewQuestionWithAnswer() {
        try {
            if (qestionIterator.hasNext()) {
                QuestionWithAnswer next = qestionIterator.next();
                return next;
            } else {
                log.info("Reseting question iterator and beginning with first question.");
                MongoCollection questions = jongo.getCollection("stevensCollection");
                MongoCursor<QuestionWithAnswer> all = questions.find("{category: 'dump questions'}").as(QuestionWithAnswer.class);
                qestionIterator = all.iterator();
                QuestionWithAnswer next = qestionIterator.next();
                return next;
            }
        } catch (MongoTimeoutException te) {
            log.error("Mongo database could not be reached at " + MONGODB_HOST + ":" + MONGODB_PORT, te);
            throw te;
        }
    }
}
