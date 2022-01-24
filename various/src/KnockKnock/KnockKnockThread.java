package KnockKnock;

import java.net.*;
import java.io.*;

public class KnockKnockThread extends Thread {

    private Socket socket = null;

    public KnockKnockThread(Socket socket) {
        super("KnockKnockMultiServerThread");
        this.socket = socket;
    }

    public void run() {

        try (

                // Input and output streams
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

        ) {

            // Communication setup
            String inputLine, outputLine;
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            // Communication
            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }

            // Terminate thread when communication ends
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
