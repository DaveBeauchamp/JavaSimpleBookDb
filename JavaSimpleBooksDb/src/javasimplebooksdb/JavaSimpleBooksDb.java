package javasimplebooksdb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.FocusEvent;
import java.io.File;
import javax.swing.JPanel;
import java.awt.event.FocusListener;



/**
 *
 * @author Lazy
 */
public class JavaSimpleBooksDb extends JFrame implements ActionListener, FocusListener
{

    private JLabel lblFrameTitle, lblNavBookId, lblNavBookIdStatus,
            lblNavBookTitle, lblNavBookTitleStatus, lblNavBookGenre,
            lblNavBookGenreStatus, lblNavBookAuthor, lblNavBookAuthorStatus,
            lblNavTitle, lblAddEditBookTitle, lblEditBookId, lblEditBookIdStatus,
            lblEditBookTitle, lblEditBookGenre, lblEditBookAuthor, lblAddEditAuthorTitle,
            lblAddEditAuthorId, lblAddEditAuthorIdStatus, lblAddEditAuthorName,
            lblAddEditStatus, lblStatusUpdate, lblNote;

    private JButton btnNavFirst, btnNavLast, btnNavPrev, btnNavNext,
            btnNavClearBook, btnNavRefreshTable, btnEditBookSave, btnEditBookNew,
            btnEditBookFirst, btnEditBookLast, btnEditBookPrev, btnEditBookNext,
            btnEditAuthorSave, btnEditAuthorNew, btnEditAuthorFirst,
            btnEditAuthorLast, btnEditAuthorPrev, btnEditAuthorNext,
            btnRefreshCombo;

    private JTextField txtBookTitle, txtBookGenre, txtAuthorName;

    private JComboBox cboAuthor;

    private JTable tblBookList;
    
    SpringLayout springLayout = new SpringLayout();
    
    public String DbNameAndPath = "E:\\GitRepos\\JavaSimpleBookDb\\JavaSimpleBooksDb\\src\\javasimplebooksdb\\Books.db";
    public File DbFile = new File(DbNameAndPath);
    
    public Database db = new Database();
    public Author authData;
    public BooksWithAuthorsName bookData;
    //public BooksWithAuthorsName[][] tableData = {};
    public String[][] tableData = {};
    
    public static void main(String[] args)
    {
        JavaSimpleBooksDb booksDb = new JavaSimpleBooksDb();
        booksDb.CreateDatabase();
        booksDb.RunSimpleBooksDb();
    }

    private void RunSimpleBooksDb()
    {
        setBounds(10, 10, 575, 600);
        setTitle("Books db");

        addWindowListener(new WindowAdapter()
        {
            public void WindowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        ShowGUIComponents();
        setResizable(false);
        setVisible(true);
    }
    
    public void CreateDatabase()
    {
        if (DbFile.exists()) { /* Do nothing */}
        else
        {
            db.CreateNewDatabase(DbNameAndPath);
            db.CreateDBTables(DbNameAndPath);
        }
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        // <editor-fold defaultstate="collapsed" desc="Author Add Edit Buttons">
        
        if (e.getSource() == btnEditAuthorSave)
        {
            if (lblAddEditAuthorIdStatus.getText() == "0")
            {
                try
                {
                    db.InsertAuthor(DbNameAndPath, txtAuthorName.getText());
                    lblStatusUpdate.setText("Successfully added an Author");
                }
                catch(Exception ex)
                {
                    lblStatusUpdate.setText("Something went wrong adding an Author");
                }        
            }
            else
            {
                try
                {
                    db.UpdateAuthor(DbNameAndPath, lblAddEditAuthorIdStatus.getText(), txtAuthorName.getText());
                    lblStatusUpdate.setText("Successfully updated an Author");
                }
                catch(Exception ex)
                {
                    lblStatusUpdate.setText("Something went wrong updating an Author");
                }        
            }
        }
        
        if (e.getSource() == btnEditAuthorNew)
        {
            lblAddEditAuthorIdStatus.setText("0");
            txtAuthorName.setText("");
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Author Nav Buttons">
        
        if (e.getSource() == btnEditAuthorFirst)
        {
            authData = new Author();
            
            try
            {
                authData = db.GetFirstAuthor(DbNameAndPath);
                lblAddEditAuthorIdStatus.setText(Long.toString(authData.GetAuthorId()));
                txtAuthorName.setText(authData.GetAuthorName());
                lblStatusUpdate.setText("First Author");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("Could not find first Author");
            }       
        }
        
        if (e.getSource() == btnEditAuthorLast)
        {
            authData = new Author();
            
            try
            {
                authData = db.GetLastAuthor(DbNameAndPath);
                lblAddEditAuthorIdStatus.setText(Long.toString(authData.GetAuthorId()));
                txtAuthorName.setText(authData.GetAuthorName());
                lblStatusUpdate.setText("Last Author");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("Could not find last Author");
            }  
        }
        
        if (e.getSource() == btnEditAuthorPrev)
        {
            authData = new Author();
            
            try
            {
                authData = db.GetPreviousAuthor(DbNameAndPath, Long.parseLong(lblAddEditAuthorIdStatus.getText()));
                lblAddEditAuthorIdStatus.setText(Long.toString(authData.GetAuthorId()));
                txtAuthorName.setText(authData.GetAuthorName());
                lblStatusUpdate.setText("Previous Author");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("This is the first Author");
            }
        }
        
        if (e.getSource() == btnEditAuthorNext)
        {
            authData = new Author();
            
            try
            {
                authData = db.GetNextAuthor(DbNameAndPath, Long.parseLong(lblAddEditAuthorIdStatus.getText()));
                lblAddEditAuthorIdStatus.setText(Long.toString(authData.GetAuthorId()));
                txtAuthorName.setText(authData.GetAuthorName());
                lblStatusUpdate.setText("Next Author");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("This is the last Author");
            }
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Book Add Edit & Nav Buttons">
        
        if (e.getSource() == btnEditBookSave)
        {
            if (lblEditBookIdStatus.getText() == "0")
            {
                try
                {
                    db.InsertBook(DbNameAndPath, txtBookTitle.getText(), txtBookGenre.getText(), Integer.toString(cboAuthor.getSelectedIndex() + 1));
                    lblStatusUpdate.setText("Successfully added a Book");
                } catch (Exception ex)
                {
                    lblStatusUpdate.setText("Something went wrong adding a Book");
                }
            }
            else
            {
                try
                {
                    db.UpdateBook(DbNameAndPath, txtBookTitle.getText(), txtBookGenre.getText(), Integer.toString(cboAuthor.getSelectedIndex() + 1), lblEditBookIdStatus.getText());
                    lblStatusUpdate.setText("Successfully updated a Book");
                } catch (Exception ex)
                {
                    lblStatusUpdate.setText("Something went wrong updating a Book");
                }
            }
        }
        
        if (e.getSource() == btnEditBookNew)
        {
            lblEditBookIdStatus.setText("0");
            txtBookTitle.setText("");
            txtBookGenre.setText("");
        }
        
        if (e.getSource() == btnEditBookFirst)
        {
            try
            {
                bookData = db.GetFirstBookAndAuthor(DbNameAndPath);
                lblEditBookIdStatus.setText(Long.toString(bookData.GetBookId()));
                txtBookTitle.setText(bookData.GetBookTitle());
                txtBookGenre.setText(bookData.GetBookGenre());
                cboAuthor.setSelectedItem(bookData.GetAuthorName());
                lblStatusUpdate.setText("First Book");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("Could not find first Book");
            }   
        }
        
        if (e.getSource() == btnEditBookLast)
        {
            try
            {
                bookData = db.GetLastBookAndAuthor(DbNameAndPath);
                lblEditBookIdStatus.setText(Long.toString(bookData.GetBookId()));
                txtBookTitle.setText(bookData.GetBookTitle());
                txtBookGenre.setText(bookData.GetBookGenre());
                cboAuthor.setSelectedItem(bookData.GetAuthorName());
                lblStatusUpdate.setText("Last Book");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("Could not find last Book");
            }
        }
        
        if (e.getSource() == btnEditBookPrev)
        {
            try
            {
                bookData = db.GetPreviousBookAndAuthor(DbNameAndPath, Long.parseLong(lblEditBookIdStatus.getText()));
                lblEditBookIdStatus.setText(Long.toString(bookData.GetBookId()));
                txtBookTitle.setText(bookData.GetBookTitle());
                txtBookGenre.setText(bookData.GetBookGenre());
                cboAuthor.setSelectedItem(bookData.GetAuthorName());
                lblStatusUpdate.setText("Previous Book");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("This is the first Book");
            }
        }
        
        if (e.getSource() == btnEditBookNext)
        {
            try
            {
                bookData = db.GetNextBookAndAuthor(DbNameAndPath, Long.parseLong(lblEditBookIdStatus.getText()));
                lblEditBookIdStatus.setText(Long.toString(bookData.GetBookId()));
                txtBookTitle.setText(bookData.GetBookTitle());
                txtBookGenre.setText(bookData.GetBookGenre());
                cboAuthor.setSelectedItem(bookData.GetAuthorName());
                lblStatusUpdate.setText("Next Book");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("This is the last Book");
            }
        }

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Book Nav Buttons">
        
        if (e.getSource() == btnNavFirst)
        {
            try
            {
                bookData = db.GetFirstBookAndAuthor(DbNameAndPath);
                lblNavBookIdStatus.setText(Long.toString(bookData.GetBookId()));
                lblNavBookTitleStatus.setText(bookData.GetBookTitle());
                lblNavBookGenreStatus.setText(bookData.GetBookGenre());
                lblNavBookAuthorStatus.setText(bookData.GetAuthorName());
                lblStatusUpdate.setText("First Book");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("Could not find first Book");
            }
        }
        
        if (e.getSource() == btnNavLast)
        {
            try
            {
                bookData = db.GetLastBookAndAuthor(DbNameAndPath);
                lblNavBookIdStatus.setText(Long.toString(bookData.GetBookId()));
                lblNavBookTitleStatus.setText(bookData.GetBookTitle());
                lblNavBookGenreStatus.setText(bookData.GetBookGenre());
                lblNavBookAuthorStatus.setText(bookData.GetAuthorName());
                lblStatusUpdate.setText("Last Book");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("Could not find last Book");
            }
        }
        
        if (e.getSource() == btnNavPrev)
        {
            try
            {
                bookData = db.GetPreviousBookAndAuthor(DbNameAndPath, Long.parseLong(lblNavBookIdStatus.getText()));
                lblNavBookIdStatus.setText(Long.toString(bookData.GetBookId()));
                lblNavBookTitleStatus.setText(bookData.GetBookTitle());
                lblNavBookGenreStatus.setText(bookData.GetBookGenre());
                lblNavBookAuthorStatus.setText(bookData.GetAuthorName());
                lblStatusUpdate.setText("Last Book");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("Could not find previous Book");
            }
        }
        
        if (e.getSource() == btnNavNext)
        {
            try
            {
                bookData = db.GetNextBookAndAuthor(DbNameAndPath, Long.parseLong(lblNavBookIdStatus.getText()));
                lblNavBookIdStatus.setText(Long.toString(bookData.GetBookId()));
                lblNavBookTitleStatus.setText(bookData.GetBookTitle());
                lblNavBookGenreStatus.setText(bookData.GetBookGenre());
                lblNavBookAuthorStatus.setText(bookData.GetAuthorName());
                lblStatusUpdate.setText("Last Book");
            }
            catch(Exception ex)
            {
                lblStatusUpdate.setText("Could not find next Book");
            }
        }
        
        if (e.getSource() == btnNavClearBook)
        {
            lblNavBookIdStatus.setText("0");
            lblNavBookTitleStatus.setText("");
            lblNavBookGenreStatus.setText("");
            lblNavBookAuthorStatus.setText("");
        }
        
        if (e.getSource() == btnNavRefreshTable)
        {
            //BookTable(springLayout);
            JavaSimpleBooksDb booksDb = new JavaSimpleBooksDb();
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            booksDb.RunSimpleBooksDb();
        }
        
        // </editor-fold>
        
        if (e.getSource() == btnRefreshCombo)
        {
            JavaSimpleBooksDb booksDb = new JavaSimpleBooksDb();
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            booksDb.RunSimpleBooksDb();
        }
        
         
    }
    
    // <editor-fold defaultstate="collapsed" desc="GUI">
    private void ShowGUIComponents()
    {
        setLayout(springLayout);
        ShowLabels(springLayout);
        ShowTextFields(springLayout);
        ShowButtons(springLayout);
        ShowComboBox(springLayout);
        BookTable(springLayout);
    }

    private void ShowLabels(SpringLayout layout)
    {
        lblFrameTitle = LibraryItems.LocateAJLabel(this, layout, "Books", 13, 13, true, 20);
        lblNavBookId = LibraryItems.LocateAJLabel(this, layout, "Book Id:", 12, 75, false, 9);
        lblNavBookIdStatus = LibraryItems.LocateAJLabel(this, layout, "", 98, 75, false, 9);
        lblNavBookTitle = LibraryItems.LocateAJLabel(this, layout, "Book Title:", 12, 98, false, 9);
        lblNavBookTitleStatus = LibraryItems.LocateAJLabel(this, layout, "", 98, 98, false, 9);
        lblNavBookGenre = LibraryItems.LocateAJLabel(this, layout, "Book Genre:", 12, 120, false, 9);
        lblNavBookGenreStatus = LibraryItems.LocateAJLabel(this, layout, "", 98, 120, false, 9);
        lblNavBookAuthor = LibraryItems.LocateAJLabel(this, layout, "Author:", 12, 142, false, 9);
        lblNavBookAuthorStatus = LibraryItems.LocateAJLabel(this, layout, "", 98, 142, false, 9);
        lblNavTitle = LibraryItems.LocateAJLabel(this, layout, "Navigation", 13, 172, true, 20);
        lblAddEditBookTitle = LibraryItems.LocateAJLabel(this, layout, "Add & Edit Books", 13, 320, true, 20);
        lblEditBookId = LibraryItems.LocateAJLabel(this, layout, "Book Id:", 12, 373, false, 9);
        lblEditBookIdStatus = LibraryItems.LocateAJLabel(this, layout, "0", 98, 373, false, 9);
        lblEditBookTitle = LibraryItems.LocateAJLabel(this, layout, "Book Title:", 12, 396, false, 9);
        lblEditBookGenre = LibraryItems.LocateAJLabel(this, layout, "Book Genre:", 12, 418, false, 9);
        lblEditBookAuthor = LibraryItems.LocateAJLabel(this, layout, "Author:", 12, 440, false, 9);
        lblAddEditAuthorTitle = LibraryItems.LocateAJLabel(this, layout, "Add & Edit Authors", 297, 320, true, 20);
        lblAddEditAuthorId = LibraryItems.LocateAJLabel(this, layout, "Author Id:", 300, 373, false, 9);
        lblAddEditAuthorIdStatus = LibraryItems.LocateAJLabel(this, layout, "0", 391, 373, false, 9);
        lblAddEditAuthorName = LibraryItems.LocateAJLabel(this, layout, "Author Name:", 300, 396, false, 9);
        lblAddEditStatus = LibraryItems.LocateAJLabel(this, layout, "Add/EditStatus:", 300, 540, false, 9);
        lblStatusUpdate = LibraryItems.LocateAJLabel(this, layout, "", 391, 540, false, 9);
    }

    private void ShowTextFields(SpringLayout layout)
    {
        txtBookTitle = LibraryItems.LocateAJTextField(this, this, layout, 10, 98, 393);
        txtBookGenre = LibraryItems.LocateAJTextField(this, this, layout, 10, 98, 415);
        txtAuthorName = LibraryItems.LocateAJTextField(this, this, layout, 10, 389, 393);
    }

    private void ShowButtons(SpringLayout layout)
    {
        btnNavFirst = LibraryItems.LocateAJButton(this, this, layout, "First", 10, 206, 75, 20);
        btnNavLast = LibraryItems.LocateAJButton(this, this, layout, "Last", 101, 206, 75, 20);
        btnNavPrev = LibraryItems.LocateAJButton(this, this, layout, "Prev", 10, 235, 75, 20);
        btnNavNext = LibraryItems.LocateAJButton(this, this, layout, "Next", 101, 235, 75, 20);
        btnNavClearBook = LibraryItems.LocateAJButton(this, this, layout, "Clear Book", 10, 264, 166, 20);
        btnNavRefreshTable = LibraryItems.LocateAJButton(this, this, layout, "Refresh Table", 10, 294, 166, 20);
        btnEditBookSave = LibraryItems.LocateAJButton(this, this, layout, "Save", 10, 477, 75, 20);
        btnEditBookNew = LibraryItems.LocateAJButton(this, this, layout, "New", 101, 477, 75, 20);
        btnEditBookFirst = LibraryItems.LocateAJButton(this, this, layout, "First", 10, 506, 75, 20);
        btnEditBookLast = LibraryItems.LocateAJButton(this, this, layout, "Last", 101, 506, 75, 20);
        btnEditBookPrev = LibraryItems.LocateAJButton(this, this, layout, "Prev", 10, 535, 75, 20);
        btnEditBookNext = LibraryItems.LocateAJButton(this, this, layout, "Next", 101, 535, 75, 20);
        btnEditAuthorSave = LibraryItems.LocateAJButton(this, this, layout, "Save", 303, 418, 75, 20);
        btnEditAuthorNew = LibraryItems.LocateAJButton(this, this, layout, "New", 389, 418, 75, 20);
        btnEditAuthorFirst = LibraryItems.LocateAJButton(this, this, layout, "First", 303, 447, 75, 20);
        btnEditAuthorLast = LibraryItems.LocateAJButton(this, this, layout, "Last", 389, 447, 75, 20);
        btnEditAuthorPrev = LibraryItems.LocateAJButton(this, this, layout, "Prev", 303, 476, 75, 20);
        btnEditAuthorNext = LibraryItems.LocateAJButton(this, this, layout, "Next", 389, 476, 75, 20);
        btnRefreshCombo = LibraryItems.LocateAJButton(this, this, layout, "Refresh", 191, 535, 80, 20);
    }

    private void ShowComboBox(SpringLayout layout)
    {
        cboAuthor = LibraryItems.LocateAJComboBox(this, this, layout, 98, 437);
        ArrayList<Author> comboList = new ArrayList<Author>();
        try
        {
            comboList = db.GetAllAuthors(DbNameAndPath);
            cboAuthor.removeAllItems();
            for (int i = 0; i < comboList.size(); i++)
            {
                cboAuthor.addItem(comboList.get(i).GetAuthorName());
            }
        }
        catch (Exception ex)
        {
            lblStatusUpdate.setText("Something went wrong getting Authors for dropdown");
        }
    }
    
    public void BookTable(SpringLayout myPanelLayout)
    {
        String[][] dataForTable = {};
        
        try
        {
            ArrayList<BooksWithAuthorsName> list = db.GetAllBooksWithAuthors(DbNameAndPath);
            dataForTable = ReadArraylistInto2DArray(list);
        }
        catch(Exception ex)
        {
            lblStatusUpdate.setText("Something went wrong adding data to the table");
        }
        
        // Create a panel to hold all other components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);

        // Create column names
        String columnNames[] =
        {
            "Book Title", "Book Genre", "Author Name", "Book Id"
        };
        
        JTable bookTable = new JTable(dataForTable, columnNames);

        // Configure some of JTable's paramters
        bookTable.isForegroundSet();
        bookTable.setShowHorizontalLines(true);
        bookTable.setRowSelectionAllowed(true);
        bookTable.setColumnSelectionAllowed(true);
        add(bookTable);

        // Change the text and background colours
        bookTable.setSelectionForeground(Color.white);
        bookTable.setSelectionBackground(Color.red);

        // Add the table to a scrolling pane, size and locate
        JScrollPane scrollPane = bookTable.createScrollPaneForTable(bookTable); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setVisible(true);
        topPanel.setPreferredSize(new Dimension(352, 305));
        myPanelLayout.putConstraint(SpringLayout.WEST, topPanel, 195, SpringLayout.WEST, this);
        myPanelLayout.putConstraint(SpringLayout.NORTH, topPanel, 12, SpringLayout.NORTH, this);

        bookTable.setOpaque(true);
        bookTable.setFillsViewportHeight(true);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // make a for loop for the table.getcolumn
        /*for (int i = 0; i < 4; i++)
        {
            bookTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }*/
    }
    
    private String[][] ReadArraylistInto2DArray(ArrayList<BooksWithAuthorsName> list)
    {

        //tableData = new BooksWithAuthorsName[list.size()][];
        tableData = new String[list.size()][4];
        for (int i = 0; i < list.size(); i++)
        {
            tableData[i][0] = list.get(i).GetBookTitle();
            tableData[i][1] = list.get(i).GetBookGenre();
            tableData[i][2] = list.get(i).GetAuthorName();
            tableData[i][3] = Long.toString(list.get(i).GetBookId());
        }

        return tableData;
    }
    
    @Override
    public void focusGained(FocusEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void focusLost(FocusEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
    
}

// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Spare Editor fold">
    
    // </editor-fold>