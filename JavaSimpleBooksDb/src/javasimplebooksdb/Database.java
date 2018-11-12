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
    
    public void InsertAuthor(String fileAndPath ,String authorName) throws SQLException
    {
        Connection conn = null;
        PreparedStatement prep = null;
        
        String query = "INSERT INTO authors (authorName) VALUES (?)";
        try      
        {
            conn = this.ConnectToDb(fileAndPath);
            conn.setAutoCommit(false);
            prep = conn.prepareStatement(query);
            prep.setString(1, authorName);
            prep.executeUpdate();
            conn.commit();
            
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            conn.rollback();
        }
        finally 
        {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        }
    }
    
    public void UpdateAuthor(String fileAndPath , String authorId ,String authorName) throws SQLException
    {
        Connection conn = null;
        PreparedStatement prep = null;
        
       String query = "UPDATE authors SET authorName = ? WHERE authorId = ?";
        try      
        {
            conn = this.ConnectToDb(fileAndPath);
            conn.setAutoCommit(false);
            prep = conn.prepareStatement(query);
            prep.setString(1, authorName);
            prep.setString(2, authorId);
            prep.executeUpdate();
            conn.commit();
            
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            conn.rollback();
        }
        finally 
        {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        }
    }
    
    // test this
    public void InsertBook(String fileAndPath ,String bookTitle, String bookGenre, String authorDropdown) throws SQLException
    {
        Connection conn = null;
        PreparedStatement prep = null;
        
        String query = "INSERT INTO books (bookTitle, bookGenre, author) VALUES (?, ?, ?)";
        try      
        {
            conn = this.ConnectToDb(fileAndPath);
            conn.setAutoCommit(false);
            prep = conn.prepareStatement(query);
            prep.setString(1, bookTitle);
            prep.setString(2, bookGenre);
            prep.setString(3, authorDropdown);
            prep.executeUpdate();
            conn.commit();
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            conn.rollback();
        }
        finally 
        {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        }
    }
    
    // test this
    public void UpdateBook(String fileAndPath , String bookTitle, String bookGenre, String authorDropdown, String bookId) throws SQLException
    {
        Connection conn = null;
        PreparedStatement prep = null;
        
       String query = "UPDATE books SET bookTitle = ?, bookGenre = ?, author = ? WHERE bookId = ?";
        try      
        {
            conn = this.ConnectToDb(fileAndPath);
            conn.setAutoCommit(false);
            prep = conn.prepareStatement(query);
            prep.setString(1, bookTitle);
            prep.setString(2, bookGenre);
            prep.setString(3, authorDropdown);
            prep.setString(4, bookId);
            prep.executeUpdate();
            conn.commit();
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            conn.rollback();
        }
        finally 
        {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        }
    }
    
    // </editor-fold>
    
    // TODO: test Author Nav Methods
    // <editor-fold defaultstate="collapsed" desc="Author Navigation Buttons">
    
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
    
    public Author GetNextAuthor(String fileAndPath, long authorId) throws SQLException
    {
        Author auth = new Author();
        // not the best way of achieveing this, parameter would be better
        String query = "SELECT * FROM authors WHERE authorId > " + authorId + " LIMIT 1";

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
    
    public Author GetPreviousAuthor(String fileAndPath, long authorId) throws SQLException
    {
        Author auth = new Author();
        // not the best way of achieveing this, parameter would be better
        String query = "SELECT * FROM authors WHERE authorId < " + authorId + " ORDER BY authorId DESC LIMIT 1";

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
    public BooksWithAuthorsName GetNextBookAndAuthor(String fileAndPath, long bookId) throws SQLException
    {
        BooksWithAuthorsName book = new BooksWithAuthorsName();
        String query = "SELECT * FROM BooksWithAuthor WHERE bookId > " + bookId + " LIMIT 1";

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
    public BooksWithAuthorsName GetPreviousBookAndAuthor(String fileAndPath, long bookId) throws SQLException
    {
        BooksWithAuthorsName book = new BooksWithAuthorsName();
        String query = "SELECT * FROM BooksWithAuthor WHERE bookId < " + bookId + " ORDER BY bookId DESC LIMIT 1";
        
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
    
    // </editor-fold>
    
    // make other methods here
    
    public ArrayList<Author> GetAllAuthors(String fileAndPath)  throws SQLException
    {
        Author tempAuth = new Author();
        ArrayList<Author> listAuth = new ArrayList<Author>();
        
        String query = "SELECT * FROM authors";
        
        try (Connection conn = ConnectToDb(fileAndPath);
             Statement stat  = conn.createStatement();
                ResultSet result = stat.executeQuery(query))
        {
            while (result.next())
            {
                tempAuth.SetAuthorId(result.getLong("authorId"));
                tempAuth.SetAuthorName(result.getString("authorName"));
                listAuth.add(tempAuth);
                tempAuth = new Author();
            }
        }
        
        return listAuth;
    }
    
}
