package Library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LibraryServerMultiThread extends Thread {

    private Socket socket = null;

    public LibraryServerMultiThread(Socket socket) {

        super("LibraryServerMultiThread");
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
            BookProtocol bp = new BookProtocol();

            out.println("Connected, enter an isbn number and click enter to search.");

            // Communication
            while ((inputLine = in.readLine()) != null) {

                outputLine = bp.getBook(inputLine).toString();
                out.println(outputLine);

            }

            // Terminate thread when communication ends
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
