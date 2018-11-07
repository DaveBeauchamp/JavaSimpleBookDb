
package javasimplebooksdb;


import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import java.awt.event.FocusListener;

public class LibraryItems 
{
    
    /** --------------------------------------------------------
    * Purpose: Locate a single JLabel within a JFrame.
    * @param   JFrame, Layout_manager, JLabel_Caption, Width, X_position, Y_Position
    * @returns The JLabel.
    * ----------------------------------------------------------
    */
    public static JLabel LocateAJLabel(JFrame myJFrame, SpringLayout myJLabelLayout, String JLabelCaption, int x, int y, boolean fontBold, int s/*, int br, int bg, int bb, boolean opaq, int fr, int fg, int fb*/)
    { 
	// Declare and Instantiate the JLabel
        JLabel myJLabel = new JLabel(JLabelCaption);
	// Add the JLabel to the screen
        myJFrame.add(myJLabel); 
	// Set the position of the JLabel (From left hand side of the JFrame (West), and from top of JFrame (North))
        myJLabelLayout.putConstraint(SpringLayout.WEST, myJLabel, x, SpringLayout.WEST, myJFrame);
        myJLabelLayout.putConstraint(SpringLayout.NORTH, myJLabel, y, SpringLayout.NORTH, myJFrame);
	// Return the label to the calling method
        if (fontBold == true)
        {
            myJLabel.setFont(new Font("", Font.PLAIN, s)); // Title label with bigger Font size
        }
        if (fontBold == false)
        {
            myJLabel.setFont(new Font("", Font.BOLD, s)); // Title label with bigger Font size
        }
        /*myJLabel.setBackground(new Color(br, bg, bb));
        myJLabel.setOpaque(opaq);
        myJLabel.setForeground(new Color(fr, fg, fb));*/
        return myJLabel;
    }
   
        
    /** --------------------------------------------------------
    * Purpose: Locate a single JTextField within a JFrame.
    * @param   JFrame, Layout_manager, Width, X_position, Y_Position
    * @returns The JTextField.
    * ----------------------------------------------------------
    */
    public static JTextField LocateAJTextField(JFrame myJFrame, FocusListener myFocusListener, SpringLayout myJTextFieldLayout, int width, int x, int y)
    {
        JTextField myJTextField = new JTextField(width);   
        myJFrame.add(myJTextField); 
        myJTextField.addFocusListener(myFocusListener);
        myJTextFieldLayout.putConstraint(SpringLayout.WEST, myJTextField, x, SpringLayout.WEST, myJFrame);
        myJTextFieldLayout.putConstraint(SpringLayout.NORTH, myJTextField, y, SpringLayout.NORTH, myJFrame);
        return myJTextField;
    }

        
    /** --------------------------------------------------------
    * Purpose: Locate a single JButton within a JFrame.
    * @param   JFrame, Layout_manager, JButton_name, JButton_caption, X_position, Y_Position, Width, Height
    * @returns The JButton.
    * ----------------------------------------------------------
    */
    public static JButton LocateAJButton(JFrame myJFrame, ActionListener myActnLstnr, SpringLayout myJButtonLayout, String  JButtonCaption, int x, int y, int w, int h)
    {    
        JButton myJButton = new JButton(JButtonCaption);
        myJFrame.add(myJButton);
        myJButton.addActionListener(myActnLstnr);
        myJButtonLayout.putConstraint(SpringLayout.WEST, myJButton, x, SpringLayout.WEST, myJFrame);
        myJButtonLayout.putConstraint(SpringLayout.NORTH, myJButton, y, SpringLayout.NORTH, myJFrame);
        myJButton.setPreferredSize(new Dimension(w,h));
        return myJButton;
    }
    
    /** --------------------------------------------------------
    * Purpose: Locate a single JTextArea within a JFrame.
    * @param   JFrame, Layout_manager, JTextArea_name, X_position, Y_Position, Width, Height
    * @returns The JTextArea.
    * ----------------------------------------------------------
    */
    public static JTextArea LocateAJTextArea(JFrame myJFrame, SpringLayout myLayout, JTextArea myJTextArea, int x, int y, int w, int h, boolean hasBorder, int br, int bg, int bb, int t)
    {    
        myJTextArea = new JTextArea(w,h);
        myJFrame.add(myJTextArea);
        myLayout.putConstraint(SpringLayout.WEST, myJTextArea, x, SpringLayout.WEST, myJFrame);
        myLayout.putConstraint(SpringLayout.NORTH, myJTextArea, y, SpringLayout.NORTH, myJFrame);
        Border areaBorder = BorderFactory.createLineBorder(new Color(br, bg, bb), t); // color color, int thickness
        if (hasBorder == true)
        {
            myJTextArea.setBorder(areaBorder);
        }
//        if (hasBorder == false)   This is sloppy but I'll live with it for now
//        {
//            return myJTextArea;
//        }
        // try to add a border here, see the room mapping border for inspiration.
        
        myJTextArea.setLineWrap(true);
//        JScrollPane incorrectScroll = new JScrollPane(myJTextArea);
//        incorrectScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return myJTextArea;
    } 
    
    public static JComboBox LocateAJComboBox(JFrame myJFrame,/* MouseListener myMouseListener,*/ SpringLayout myJTextFieldLayout, int x, int y)
    {
        JComboBox myJComboBox = new JComboBox();   
        myJFrame.add(myJComboBox); 
//        myJComboBox.addMouseListener(myMouseListener);
        myJTextFieldLayout.putConstraint(SpringLayout.WEST, myJComboBox, x, SpringLayout.WEST, myJFrame);
        myJTextFieldLayout.putConstraint(SpringLayout.NORTH, myJComboBox, y, SpringLayout.NORTH, myJFrame);
        myJComboBox.setPrototypeDisplayValue("XXXXXXXX"); // this sets all comboxes to this size will have to change this a to local version later though in the method at the moment serves my purposes (NOT FUTURE PROOF) 
        return myJComboBox;
    }
    
}
