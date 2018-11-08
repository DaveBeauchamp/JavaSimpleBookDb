package javasimplebooksdb;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Lazy
 */
public class Database
{       
    
    public class SQLiteJDBCDriverConnection 
    {
        /**
         * Connect to a sample database
         */
        public void ConnectToDb(String fileAndPath)
        {
            Connection conn = null;
            try
            {
                String url = "jdbc:sqlite:" + fileAndPath;   
                conn = DriverManager.getConnection(url);

                System.out.println("Database connection successful!");

            } 
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            } 
            finally
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
        }
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
    
    // make other methods here

}
