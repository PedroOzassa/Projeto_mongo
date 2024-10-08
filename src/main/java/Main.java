import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String uri = "mongodb+srv://fitznieri:qwerty12345@chat.htme9.mongodb.net/?retryWrites=true&w=majority&appName=Chat" ;
        String dbName = "Chat";

        MongoDBConnection mongoDBConnection = new MongoDBConnection(uri, dbName);

        boolean exit = false;

        while (!exit) {
            System.out.println("=== Menu de Login ===");
            System.out.println("1. Login");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Usuário: ");
                    String username = scanner.nextLine();

                    System.out.print("Senha: ");
                    String password = scanner.nextLine();

                    if (authenticateUser(mongoDBConnection, username, password)) {
                        System.out.println("Login bem-sucedido! Bem-vindo, " + username);
                    } else {
                        System.out.println("Usuário ou senha inválidos.");
                    }
                    break;

                case 2:
                    exit = true;
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        mongoDBConnection.close();
        System.out.println("Programa encerrado.");
    }

    public static boolean authenticateUser(MongoDBConnection mongoDBConnection, String username, String password) {
        MongoCollection<Document> collection = mongoDBConnection.getDatabase().getCollection("usuarios");

        Document query = new Document("username", username);
        Document userDoc = collection.find(query).first();

        if (userDoc != null) {
            String storedPassword = userDoc.getString("password");
            return storedPassword.equals(password);
        }

        return false;
    }
}