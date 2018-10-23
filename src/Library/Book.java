package Library;

public class Book {

    private int isbn;
    private String title;
    private String subTitle;
    private String authors;

    // Constructors
    public Book(int isbn, String title, String subTitle, String authors) {

        this.isbn = isbn;
        this.title = title;
        this.subTitle = subTitle;
        this.authors = authors;

    }

    public Book() {

    }

    // Setters
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    // Getters
    public int getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return isbn + "/" + title + "/" + subTitle + "/" + authors;
    }

}
