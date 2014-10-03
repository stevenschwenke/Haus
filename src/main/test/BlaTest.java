import com.mongodb.*;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by steven on 02.10.14.
 */
public class BlaTest {

    @Test
    public void showAll() throws UnknownHostException {

        // TODO Run Mongo in Secure Mode, then use this:
        //MongoCredential credential = MongoCredential.createMongoCRCredential(userName, database, password);
        //MongoClient mongoClient = new MongoClient(new ServerAddress(), Arrays.asList(credential));
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("mydb");

        System.out.println("Collections: ");
        for (String s : db.getCollectionNames()) {
            System.out.println(s);
        }

        DBCollection stevensCollection = db.getCollection("stevensCollection");

        System.out.println("Documents:");
        DBCursor cursor = stevensCollection.find();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

        mongoClient.close();
    }

    @Test
    public void listAllDumpQuestions() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("mydb");
        DBCollection stevensCollection = db.getCollection("stevensCollection");

        BasicDBObject query = new BasicDBObject("category", "dump questions");

        DBCursor cursor = stevensCollection.find(query);

        try {
            while (cursor.hasNext()) {
                DBObject nextThing = cursor.next();
                System.out.println(nextThing);
            }
        } finally {
            cursor.close();
        }
        mongoClient.close();
    }

    @Test
    public void mappingWithJongo() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("mydb");
        Jongo jongo = new Jongo(db);
        MongoCollection questions = jongo.getCollection("stevensCollection");

        MongoCursor<QuestionWithAnswer> all = questions.find("{category: 'dump questions'}").as(QuestionWithAnswer.class);
        Iterator<QuestionWithAnswer> iterator = all.iterator();
        while (iterator.hasNext()) {
            QuestionWithAnswer next = iterator.next();
            System.out.println(next);
        }
    }

    @Test
    public void fuelleLernkartenbeispiele() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("mydb");

        DBCollection stevensCollection = db.getCollection("stevensCollection");
        BasicDBObject karte1 = new BasicDBObject("question", "Was ist das graue Monster?")
                .append("answer", "Das graue Monster ist grau.")
                .append("category", "dump questions");
        stevensCollection.insert(karte1);

        BasicDBObject karte2 = new BasicDBObject("question", "Wieviele Katzen gibt es auf Keck?")
                .append("answer", "Jetzt leider keine mehr.")
                .append("category", "dump questions");
        stevensCollection.insert(karte2);

        mongoClient.close();
    }

    // TODO Dringlichkeitsgrad in Objekte speichern und nach Dringilichkeitsgrad anzeigen mit:
    // find all where i > 50
//    query = new BasicDBObject("i", new BasicDBObject("$gt", 50));
//
//    cursor = coll.find(query);
//    try {
//        while (cursor.hasNext()) {
//            System.out.println(cursor.next());
//        }
//    } finally {
//        cursor.close();
//    }

}
