package javasimplebooksdb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import javax.swing.JPanel;
import java.net.*;
import java.io.*;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;

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
            btnEditAuthorLast, btnEditAuthorPrev, btnEditAuthorNext;

    private JTextField txtBookTitle, txtBookGenre, txtAuthorName;

    private JComboBox cboAuthor;

    private JTable tblBookList;

    public static void main(String[] args)
    {
        JavaSimpleBooksDb booksDb = new JavaSimpleBooksDb();
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

    // <editor-fold defaultstate="collapsed" desc="GUI">
    private void ShowGUIComponents()
    {
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        // add the components methods here
        ShowLabels(springLayout);
        ShowTextFields(springLayout);
        ShowButtons(springLayout);

    }

    private void ShowLabels(SpringLayout layout)
    {
        lblFrameTitle = LibraryItems.LocateAJLabel(this, layout, "Books", 13, 13, true, 20);
        lblNavBookId = LibraryItems.LocateAJLabel(this, layout, "Book Id:", 12, 75, false, 9);
        lblNavBookIdStatus = LibraryItems.LocateAJLabel(this, layout, "placeholder", 98, 75, false, 9);
        lblNavBookTitle = LibraryItems.LocateAJLabel(this, layout, "Book Title:", 12, 98, false, 9);
        lblNavBookTitleStatus = LibraryItems.LocateAJLabel(this, layout, "placeholder", 98, 98, false, 9);
        lblNavBookGenre = LibraryItems.LocateAJLabel(this, layout, "Book Genre:", 12, 120, false, 9);
        lblNavBookGenreStatus = LibraryItems.LocateAJLabel(this, layout, "placeholder", 98, 120, false, 9);
        lblNavBookAuthor = LibraryItems.LocateAJLabel(this, layout, "Author:", 12, 142, false, 9);
        lblNavBookAuthorStatus = LibraryItems.LocateAJLabel(this, layout, "placeholder", 98, 142, false, 9);
        lblNavTitle = LibraryItems.LocateAJLabel(this, layout, "Navigation", 13, 172, true, 20);
        lblAddEditBookTitle = LibraryItems.LocateAJLabel(this, layout, "Add & Edit Books", 13, 320, true, 20);
        lblEditBookId = LibraryItems.LocateAJLabel(this, layout, "Book Id:", 12, 373, false, 9);
        lblEditBookIdStatus = LibraryItems.LocateAJLabel(this, layout, "placeholder", 98, 373, false, 9);
        lblEditBookTitle = LibraryItems.LocateAJLabel(this, layout, "Book Title:", 12, 396, false, 9);
        lblEditBookGenre = LibraryItems.LocateAJLabel(this, layout, "Book Genre:", 12, 418, false, 9);
        lblEditBookAuthor = LibraryItems.LocateAJLabel(this, layout, "Author:", 12, 440, false, 9);
        lblAddEditAuthorTitle = LibraryItems.LocateAJLabel(this, layout, "Add & Edit Authors", 297, 320, true, 20);
        lblAddEditAuthorId = LibraryItems.LocateAJLabel(this, layout, "Author Id:", 300, 373, false, 9);
        lblAddEditAuthorIdStatus = LibraryItems.LocateAJLabel(this, layout, "placeholder", 391, 373, false, 9);
        lblAddEditAuthorName = LibraryItems.LocateAJLabel(this, layout, "Author Name:", 300, 396, false, 9);
        lblAddEditStatus = LibraryItems.LocateAJLabel(this, layout, "Add/EditStatus:", 300, 540, false, 9);
        lblStatusUpdate = LibraryItems.LocateAJLabel(this, layout, "placeholder", 391, 540, false, 9);
        lblNote = LibraryItems.LocateAJLabel(this, layout, "Make Table later", 300, 80, true, 20);
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
        btnEditAuthorNext = LibraryItems.LocateAJButton(this, this, layout, "Prev", 389, 476, 75, 20);
    }

    public void QuestionPanel(SpringLayout myPanelLayout)
    {
        // Create a panel to hold all other components
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());
        add(questionPanel);

        // Add the table to a scrolling pane, size and locate
        questionPanel.setPreferredSize(new Dimension(250, 156));
        myPanelLayout.putConstraint(SpringLayout.WEST, questionPanel, 5, SpringLayout.WEST, this);
        myPanelLayout.putConstraint(SpringLayout.NORTH, questionPanel, 90, SpringLayout.NORTH, this);
        Border questionBorder = BorderFactory.createLineBorder(new Color(0, 0, 140), 3);
        questionPanel.setBorder(questionBorder);
        questionPanel.setBackground(new Color(176, 224, 230));

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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


// <editor-fold defaultstate="collapsed" desc="Overrides">
/*@Override
        public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    }*/
    
    // </editor-fold>
     

