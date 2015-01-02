import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Delivery of data for the questions.
 * <p>
 * Created by steven on 18.09.14.
 */
public class QuestionStore {

    private Log log = LogFactory.getLog(QuestionStore.class);

    /**
     * Host where the database can be reached
     */
    public static final String DB_CONNECTION_STRING = "remote:localhost/documentTest";

    public static final String DB_USER_NAME = "root";

    public static final String DB_PASSWORD = "orientdb";

    /**
     * OrientDB
     */
    private ODatabaseDocumentTx orientDB;

    public QuestionStore() {
        setupConnectionToOrientDB();
    }

    private void setupConnectionToOrientDB() {
        orientDB = new ODatabaseDocumentTx(DB_CONNECTION_STRING).open(DB_USER_NAME, DB_PASSWORD);
    }


    public QuestionWithAnswer getNewQuestionWithAnswer() {

//        try {
        long count = orientDB.countClass("QuestionWithAnswer");
        System.out.println(count + " documents in the database:");

        List<QuestionWithAnswer> questionWithAnswerList = new ArrayList<>((int) count);

        for (ODocument animal : orientDB.browseClass("QuestionWithAnswer")) {
            System.out.println(animal.field("id") + ": " + animal.field("question") + " -> " + animal.field
                    ("answer"));
            QuestionWithAnswer newItem = new QuestionWithAnswer(animal.field("question"), animal.field("answer"));
            questionWithAnswerList.add(newItem);
        }

        int randomIndex = (int) (Math.random() * count);
        System.out.println("Random index = " + randomIndex);

        return questionWithAnswerList.get(randomIndex);
//        }
//        finally {
        // TODO Close database when ready:
//            orientDB.close();
//        }

    }
}
