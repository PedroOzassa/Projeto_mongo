import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoDBConnection {
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public MongoDBConnection(String uri, String dbName) {
        ConnectionString connectionString = new ConnectionString(uri);
        mongoClient = MongoClients.create(connectionString);
        mongoDatabase = mongoClient.getDatabase(dbName);
    }

    public MongoDatabase getDatabase() {
        return mongoDatabase;
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public void addMessage(String sender, String recipient, String messageContent) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("mensagens");

        Document message = new Document("sender", sender)
                .append("recipient", recipient)
                .append("content", messageContent);

        collection.insertOne(message);
        System.out.println("Mensagem adicionada: " + message.toJson());
    }
}