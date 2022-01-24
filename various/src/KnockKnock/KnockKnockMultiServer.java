package KnockKnock;

import java.io.IOException;
import java.net.ServerSocket;

public class KnockKnockMultiServer {

    public static void run() {

        // Get port number
        int portNumber = 8088;

        // Start listening for clients to connect
        boolean listening = true;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            while (listening) {
                // Start a new Thread with helper object which is a newly created socket from the server when a client connects
                new KnockKnockThread(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);

        }

    }

}
