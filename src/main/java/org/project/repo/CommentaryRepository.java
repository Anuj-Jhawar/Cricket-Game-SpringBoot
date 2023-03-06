package org.project.repo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.project.model.BallCommentary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public class CommentaryRepository {

    private static final MongoClient mongoClient = MongoClients.create();
    private static final String DATABASE_NAME = "CommentaryDB";
    private static final String COLLECTION_NAME = "Commentary";
    private static final MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
    private static final MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentaryRepository.class);

    public void addCommentary(int matchId) {
        /*
            Add commentary document for the match.
        */
        ArrayList<Document> Innings1 = new ArrayList<>();
        ArrayList<Document> Innings2 = new ArrayList<>();
        Document document = new Document();
        document.append("match_id", matchId);
        document.append("Innings1", Innings1);
        document.append("Innings2", Innings2);
        collection.insertOne(document);
    }

    public Document findDocument(int matchId) {
        /*
            Find commentary document for the match.
        */
        Document query = new Document("match_id", matchId);
        return collection.find(query).first();
    }

    public Document createDocument(BallCommentary ballCommentary) {
        /*
            Create commentary document for a ball.
        */
        Document document = new Document();
        document.append("batsman_id", ballCommentary.getBatsmanId());
        document.append("bowler_id", ballCommentary.getBowlerId());
        document.append("commentary_text", ballCommentary.getCommentaryText());
        return document;
    }

    public void updateCommentary(int matchId, BallCommentary ballCommentary, int inningNo) {
        /*
            Update commentary document of the match.
        */
        Document document = this.findDocument(matchId);
        if (document == null) {
            this.addCommentary(matchId);
            document = this.findDocument(matchId);
        }
        Document newDocument = createDocument(ballCommentary);
        Document filter = new Document("match_id", matchId);
        Document update;
        if (inningNo == 0) {
            update = new Document("$push", new Document("Innings1", newDocument));
        } else {
            update = new Document("$push", new Document("Innings2", newDocument));
        }
        collection.updateOne(filter, update);
    }

    public ArrayList<ArrayList<Document>> getCommentary(int matchId) {
        /*
            Return commentary document of the match.
        */
        try {
            Document query = new Document("match_id", matchId);
            Document document = collection.find(query).first();
            ArrayList<ArrayList<Document>> commentary = new ArrayList<>();
            commentary.add((ArrayList<Document>) document.get("Innings1"));
            commentary.add((ArrayList<Document>) document.get("Innings2"));
            return commentary;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
