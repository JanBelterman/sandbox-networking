package KnockKnock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class KnockKnockServer {

    public static void run() throws Exception {

        // Get port number from arguments
        int portNumber = 8088;

        try (

                // Create socket from port number and (this IP address?)
                ServerSocket serverSocket = new ServerSocket(portNumber);

                // Accepting a connection from the client
                // Blocking operation (stops program till client input)
                Socket clientSocket = serverSocket.accept();

                // Create object that talks to the client (output)
                // Server outputStream = Client inputStream
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);

                // Create object that get input from client (input)
                // Server inputStream = Client outputStream
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()))

        ) {

            // Define communication strings
            String inputLine, outputLine;

            // Initiate conversation with client

            // Get message from protocol
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);

            // Send it to the user
            out.println(outputLine);

            // Communication loop
            // If user responds get next output line from protocol and send it to the user
            while ((inputLine = in.readLine()) != null) { // Also a blocking statement
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                // If the protocol's new line is "Bye.' than end the communication loop
                if (outputLine.equals("Bye."))
                    break;
            }

        }

    }

}
