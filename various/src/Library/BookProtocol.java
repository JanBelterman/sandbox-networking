package Library;

import Library.Book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BookProtocol {

    public Book getBook(String isbn) {

        Book book = null;

        try {

            // Setup url
            URL bookInfo = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);

            // Open file stream from url
            BufferedReader in = new BufferedReader(new InputStreamReader(bookInfo.openStream()));
            // Read all lines of the content package
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            // Close (clear connection) (java garbage collection triggered)
            in.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        // Test code
        book = new Book(1000, "Today", "Is a nice day", "Jan Belterman");

        return book;

    }

}
