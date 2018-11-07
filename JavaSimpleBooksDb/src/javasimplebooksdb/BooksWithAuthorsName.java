
package javasimplebooksdb;

/**
 *
 * @author Lazy
 */
// Books Class to handle data for the BooksWithAuthor view (the voew joins the tables)
public class BooksWithAuthorsName
{
    private String BookTitle;
    public String GetBookTitle() { return this.BookTitle; }
    public void SetBookTitle(String BookTitle) { this.BookTitle = BookTitle; }
    
    private String BookGenre;
    public String GetBookGenre() { return this.BookGenre; }
    public void SetBookGenre(String BookGenre) { this.BookGenre = BookGenre; }
    
    private String AuthorName;
    public String GetAuthorName() { return this.AuthorName; }
    public void SetAuthorName(String AuthorName) { this.AuthorName = AuthorName; }
    
    private long BookId;
    public long GetBookId() { return this.BookId; }
    public void SetBookId(long BookId) { this.BookId = BookId;}
    
}
