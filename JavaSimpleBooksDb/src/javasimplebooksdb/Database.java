package javasimplebooksdb;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Lazy
 */
public class Database
{ 
    /**
     * Connect to a sample database
     */
    public Connection ConnectToDb(String fileAndPath)
    {
        Connection conn = null;
        try
        {
            String url = "jdbc:sqlite:" + fileAndPath;
            conn = DriverManager.getConnection(url);

            System.out.println("Database connection successful!");

        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        } finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }

    public void CreateNewDatabase(String fileAndPath)
    {
        String url = "jdbc:sqlite:" + fileAndPath;   
        
        try(Connection conn = DriverManager.getConnection(url))
        {
            if(conn != null)
            {
                System.out.println("Database created succesfully!");
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public void CreateDBTables(String fileAndPath)
    {
        String url = "jdbc:sqlite:" + fileAndPath;
        // will have to find a better way of doing this
        String booksTable = "CREATE TABLE IF NOT EXISTS books (bookId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "bookTitle TEXT NOT NULL,\n"
                + "bookGenre TEXT NOT NULL,\n"
                + "author INTEGER NOT NULL,\n"
                + "FOREIGN KEY (author) REFERENCES authors(authorID));\n";
        String authorsTable = "CREATE TABLE IF NOT EXISTS authors (authorId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "authorName TEXT NOT NULL);\n";
        String viewBooksWithAuthor = "CREATE VIEW IF NOT EXISTS BooksWithAuthor AS\n"
                + "SELECT bookId, bookTitle, bookGenre, authorName\n"
                + "FROM books\n"
                + "INNER JOIN authors ON authors.authorId = books.author;";

        ArrayList<String> dbTables = new ArrayList<String>();
        dbTables.add(booksTable);
        dbTables.add(authorsTable);
        dbTables.add(viewBooksWithAuthor);

        for (String dbTable : dbTables)
        {
            try (Connection conn = DriverManager.getConnection(url);
                    Statement st = conn.createStatement())
            {
                st.execute(dbTable);
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    // TODO: test Add Edit Methods
    // <editor-fold defaultstate="collapsed" desc="Add Edit Methods">
    
    // test this
    public void InsertAuthor(String fileAndPath ,String authorName) throws SQLException
    {
        String query = "INSERT INTO authors (authorName) VALUES (?)";
        Connection conn = this.ConnectToDb(fileAndPath);
        PreparedStatement prep = conn.prepareStatement(query);
        try      
        {
            prep.setString(1, authorName);
            prep.executeUpdate();
            prep.getConnection().commit();
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            prep.getConnection().rollback();
        }
    }
    
    // test this
    public void UpdateAuthor(String fileAndPath , String authorId ,String authorName) throws SQLException
    {
       String query = "UPDATE authors SET authorName = ? WHERE authorId = ?";
        Connection conn = this.ConnectToDb(fileAndPath);
        PreparedStatement prep = conn.prepareStatement(query);
        try      
        {
            prep.setString(1, authorName);
            prep.setString(2, authorId);
            prep.executeUpdate();
            prep.getConnection().commit();
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            prep.getConnection().rollback();
        } 
    }
    
    // test this
    public void InsertBook(String fileAndPath ,String bookTitle, String bookGenre, String authorDropdown) throws SQLException
    {
        String query = "INSERT INTO books (bookTitle, bookGenre, author) VALUES (?, ?, ?)";
        Connection conn = this.ConnectToDb(fileAndPath);
        PreparedStatement prep = conn.prepareStatement(query);
        try      
        {
            prep.setString(1, bookTitle);
            prep.setString(2, bookGenre);
            prep.setString(3, authorDropdown);
            prep.executeUpdate();
            prep.getConnection().commit();
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            prep.getConnection().rollback();
        }
    }
    
    // test this
    public void UpdateBook(String fileAndPath , String bookTitle, String bookGenre, String authorDropdown, String bookId) throws SQLException
    {
       String query = "UPDATE books SET bookTitle = ?, bookGenre = ?, author = ? WHERE bookId = ?";
        Connection conn = this.ConnectToDb(fileAndPath);
        PreparedStatement prep = conn.prepareStatement(query);
        try      
        {
            prep.setString(1, bookTitle);
            prep.setString(2, bookGenre);
            prep.setString(3, authorDropdown);
            prep.setString(4, bookId);
            prep.executeUpdate();
            prep.getConnection().commit();
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            prep.getConnection().rollback();
        } 
    }
    
    // </editor-fold>
    
    // TODO: test Author Nav Methods
    // <editor-fold defaultstate="collapsed" desc="Author Navigation Buttons">
    
    // test this
    public Author GetFirstAuthor(String fileAndPath) throws SQLException
    {
        Author auth = new Author();
        String query = "SELECT * FROM authors ORDER BY authorId ASC LIMIT 1";
        
        try (Connection conn = this.ConnectToDb(fileAndPath);
        Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery(query))
        {
            auth.SetAuthorId(result.getLong("authorId"));
            auth.SetAuthorName(result.getString("authorName"));
            return auth;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());        
            return auth = new Author();
        }
    }
    
    // test this
    public Author GetLastAuthor(String fileAndPath) throws SQLException
    {
        Author auth = new Author();
        String query = "SELECT * FROM authors ORDER BY authorId DESC LIMIT 1";
        
        try (Connection conn = this.ConnectToDb(fileAndPath);
        Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery(query))
        {
            auth.SetAuthorId(result.getLong("authorId"));
            auth.SetAuthorName(result.getString("authorName"));
            return auth;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());        
            return auth = new Author();
        }
    }
    
    // test this
    public Author GetNextAuthor(String fileAndPath, String authorId) throws SQLException
    {
        Author auth = new Author();
        String query = "SELECT * FROM authors WHERE authorId > ? LIMIT 1";
        
        try (Connection conn = this.ConnectToDb(fileAndPath);
        PreparedStatement prep = conn.prepareStatement(query);
        ResultSet result = prep.executeQuery(query))
        {
            prep.setString(1, authorId);
            auth.SetAuthorId(result.getLong("authorId"));
            auth.SetAuthorName(result.getString("authorName"));
            return auth;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());        
            return auth = new Author();
        } 
    }
    
    // test this
    public Author GetPreviousAuthor(String fileAndPath, String authorId) throws SQLException
    {
        Author auth = new Author();
        String query = "SELECT * FROM authors WHERE authorId < ? ORDER BY authorId DESC LIMIT 1";
        
        try (Connection conn = this.ConnectToDb(fileAndPath);
        PreparedStatement prep = conn.prepareStatement(query);
        ResultSet result = prep.executeQuery(query))
        {
            prep.setString(1, authorId);
            auth.SetAuthorId(result.getLong("authorId"));
            auth.SetAuthorName(result.getString("authorName"));
            return auth;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());        
            return auth = new Author();
        } 
    }
    
    // </editor-fold>
    
    // TODO: write and test Book Nav methods 
    // <editor-fold defaultstate="collapsed" desc="All Books Navigation">
    
    // test this
    public BooksWithAuthorsName GetFirstBookAndAuthor(String fileAndPath) throws SQLException
    {
        BooksWithAuthorsName book = new BooksWithAuthorsName();
        String query = "SELECT * FROM BooksWithAuthor ORDER BY bookId ASC LIMIT 1";
        
        try (Connection conn = this.ConnectToDb(fileAndPath);
        Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery(query))
        {
            book.SetBookId(result.getLong("bookId"));
            book.SetBookTitle(result.getString("bookTitle"));
            book.SetBookGenre("bookGenre");
            book.SetAuthorName("author");
            return book;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());        
            return book = new BooksWithAuthorsName();
        }
    }
    
    // test this
    public BooksWithAuthorsName GetLastBookAndAuthor(String fileAndPath) throws SQLException
    {
        BooksWithAuthorsName book = new BooksWithAuthorsName();
        String query = "SELECT * FROM BooksWithAuthor ORDER BY bookId DESC LIMIT 1";
        
        try (Connection conn = this.ConnectToDb(fileAndPath);
        Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery(query))
        {
            book.SetBookId(result.getLong("bookId"));
            book.SetBookTitle(result.getString("bookTitle"));
            book.SetBookGenre("bookGenre");
            book.SetAuthorName("author");
            return book;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());        
            return book = new BooksWithAuthorsName();
        }
    }
    
    // test this
    public BooksWithAuthorsName GetNextBookAndAuthor(String fileAndPath, String bookId) throws SQLException
    {
        BooksWithAuthorsName book = new BooksWithAuthorsName();
        String query = "SELECT * FROM BooksWithAuthor WHERE bookId > ? LIMIT 1";
        
        try (Connection conn = this.ConnectToDb(fileAndPath);
        PreparedStatement prep = conn.prepareStatement(query);
        ResultSet result = prep.executeQuery(query))
        {
            prep.setString(1, bookId);
            book.SetBookId(result.getLong("bookId"));
            book.SetBookTitle(result.getString("bookTitle"));
            book.SetBookGenre("bookGenre");
            book.SetAuthorName("author");
            return book;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());        
            return book = new BooksWithAuthorsName();
        } 
    }
    
    // test this
    public BooksWithAuthorsName GetPreviousBookAndAuthor(String fileAndPath, String bookId) throws SQLException
    {
        BooksWithAuthorsName book = new BooksWithAuthorsName();
        String query = "SELECT * FROM BooksWithAuthor WHERE bookId < ? ORDER BY bookId DESC LIMIT 1";
        
        try (Connection conn = this.ConnectToDb(fileAndPath);
        PreparedStatement prep = conn.prepareStatement(query);
        ResultSet result = prep.executeQuery(query))
        {
            prep.setString(1, bookId);
            book.SetBookId(result.getLong("bookId"));
            book.SetBookTitle(result.getString("bookTitle"));
            book.SetBookGenre("bookGenre");
            book.SetAuthorName("author");
            return book;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());        
            return book = new BooksWithAuthorsName();
        } 
    }
    
    
    // </editor-fold>
    
    // make other methods here

}
