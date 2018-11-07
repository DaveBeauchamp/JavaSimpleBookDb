
package javasimplebooksdb;

/**
 *
 * @author Lazy
 */
// Author Class to handle data for the Author Table
public class Author
{
    // Getters and setters
    private long AuthorId;
    public long GetAuthorId() { return this.AuthorId; }
    public void SetAuthorId(long AuthorId) { this.AuthorId = AuthorId; }
    
    private String AuthorName;
    public String GetAuthorName() { return this.AuthorName; }
    public void SetAuthorName(String AuthorName) { this.AuthorName = AuthorName; }
    
}
