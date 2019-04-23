package PlainJavaHttpServer;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class ClientSocket extends Thread {

    private static final String FILE_FOLDER = "src/PlainJavaHttpServer/";

    private Socket socket;

    ClientSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Client connected: " + socket.getInetAddress() + ":" + socket.getPort());
        BufferedReader in = null; PrintWriter out = null; BufferedOutputStream dataOut = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            dataOut = new BufferedOutputStream(socket.getOutputStream());

            String requestString = in.readLine();
            String headerLine = requestString;

            // HeaderLine is null sometimes
            if (headerLine == null)
                return;

            StringTokenizer tokenizer = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();
            String httpQuery = tokenizer.nextToken();

            // Print http request contents
            while (in.ready()) {
                System.out.println(requestString);
                requestString = in.readLine();
            }

            // TODO: Handle other headers

            // Check method
            if (httpMethod.equals("GET")) {
                // Get html
                File file = new File(FILE_FOLDER + "index.html");
                int fileLength = (int) file.length();
                byte[] fileData = readFileData(file, fileLength);

                // Response Headers
                out.print("HTTP/1.1 200 OK\r\n");
                out.print("Date: " + new Date() + "\r\n");
                out.print("Content-type: text/html" + "\r\n");
                out.print("Content-length: " + fileLength + "\r\n");
                out.print("\r\n"); // Blank line to separate headers and body
                out.flush();

                // Response Body
                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();

                System.out.println("Response send");
            }

        } catch (Exception e) {
            System.err.println("Error while handling request: " + e);
        } finally {
            // Closes socket, writers & reader
            try {
                socket.close();
                in.close();
                out.close();
                dataOut.close();
            } catch (Exception e) {
                System.err.println("Error while closing socket connection: " + e.getMessage());
            }
        }
    }

    private static byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

}
