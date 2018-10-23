package Smtp;

import java.io.IOException;
import java.net.ServerSocket;

public class SmtpDriver {

    public static void run() {

        // Defining port number
        int portNumber = 25;
        // Boolean that indicates if program is listening
        boolean listening = true;
        // Create server socket on the already defined port
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            // A loop that continues until listening is set to false
            while (listening) {
                // Listen for a connection to the socket
                new SmtpContext(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            // Print an error
            System.err.println("Could not listen on port " + portNumber);
            // Exit program with exit code -1
            System.exit(-1);
        }

    }

}
