import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoTimeoutException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jongo.Find;
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

    public QuestionStore() {
        setupConnectionToDatabase();
    }

    /**
     * Connects to the database.
     */
    private void setupConnectionToDatabase() {
        log.info("Connecting to database at " + MONGODB_HOST + ":" + MONGODB_PORT);
        try {
            MongoClient mongoClient = new MongoClient(MONGODB_HOST, MONGODB_PORT);
            DB db = mongoClient.getDB("mydb");
            jongo = new Jongo(db);
        } catch (UnknownHostException e) {
            log.error("Unknown Host: " + MONGODB_HOST, e);
            throw new RuntimeException(e);
        }
    }

    public QuestionWithAnswer getNewQuestionWithAnswer() {
        try {
            MongoCollection questions = jongo.getCollection("questions");

            // from http://stackoverflow.com/questions/2824157/random-record-from-mongodb - skip() is rather
            // inefficient since it has to scan that many documents. Also, there is a race condition if rows are
            // removed between getting the count and running the query. However, the questions won't get deleted
            // during runtime so the race confition doesn't matter.
            int randomRecordIndex = (int) (Math.random() * questions.count());

            // TODO: find the questions that should be repeated first with questions.find("{category: 'dump
            // questions'}") and adapt generation of random number above to prevent out-of-range-errors.
            Find find = questions.find();

            // Negative number is like a positive number but prevents creation of a cursor. Hence,
            // only one item will be found here:
            MongoCursor<QuestionWithAnswer> all = find.limit(-1).skip(randomRecordIndex).as(QuestionWithAnswer.class);
            return all.next();
        } catch (MongoTimeoutException te) {
            log.error("Mongo database could not be reached at " + MONGODB_HOST + ":" + MONGODB_PORT, te);
            throw te;
        }
    }
}
