package PlainJavaHttpServer;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer {

    private static final int PORT = 8080;

    // Start server and listen for clients
    public static void main(String[] args) {
        try {

            // Create server
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started listening for connections on port: " + PORT + "...");

            // Listen for clients
            while (true) {
                ClientSocket clientSocket = new ClientSocket(serverSocket.accept()); // BLOCKING
                clientSocket.start();
            }

        } catch (IOException e) {
            System.err.println("Connection error : " + e.getMessage());
        }
    }

}
