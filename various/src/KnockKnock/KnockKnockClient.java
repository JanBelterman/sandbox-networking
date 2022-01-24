package KnockKnock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class KnockKnockClient {

    public static void run() throws Exception {

        // Get connection values from args
        // THIS IS THE SOCKET (IP address + port number)
        String hostName = "192.168.178.16"; // IP address
        int port = 8088; // Port number

        try (

                // Create socket with IP and port number
                Socket socket = new Socket(hostName, port);

                // Object that sends information from client to server
                // Client outputStream = Server inputStream
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Get server input
                // Client inputStream = Server outputStream
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

        ) {

            // Get scanner for console input (the client)
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));

            // Defining server and client strings
            String fromServer, fromUser;

            // Communication loop
            // While the server returns something
            while ((fromServer = in.readLine()) != null) {

                // Print message from server
                System.out.println("Server: " + fromServer);
                // If server msg = "Bye. " than end communication loop
                if (fromServer.equals("Bye."))
                    break;

                // If server msg != "Bye. " than ask for user input
                fromUser = stdIn.readLine();
                // Print user input and send to server
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }

            }

        }

    }

}
