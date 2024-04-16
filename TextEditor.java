import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.*;
import java.util.List;

//TextEditor class starts here
class TextEditor extends Frame implements ActionListener, ItemListener {
    JTextArea ta = new JTextArea();
    int i, len1, len, pos1;
    String str = "", s3 = "", s2 = "", s4 = "", s32 = "", s6 = "", s7 = "", s8 = "", s9 = "", filePath = "", strFind = "", strReplace = "";
    String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };
    CheckboxMenuItem chkb = new CheckboxMenuItem("Word Wrap");
    Highlighter highlighter;
    Highlighter.HighlightPainter painter;
    private Integer[] fontSizes = {9, 10, 11, 12, 13, 14, 15, 16,17,18,19,20,22,24,26,28,30};
    JComboBox fontList = new JComboBox(fontSizes);

    Font font;
    Integer fontStyle, fontSize;

    JToolBar tlBar;

    JLabel lbFontSize;



    public TextEditor() {
        MenuBar mb = new MenuBar();
        setLayout(new BorderLayout());
        add("Center", ta);

        //Add font bar to JTextPanel
        tlBar = new JToolBar();
        JButton button_FontPlain = new JButton("Plain");
        JButton button_FontBold = new JButton("Bold");
        JButton button_FontItalic = new JButton("Italic");
        button_FontBold.setVisible(true);
        button_FontBold.setBounds(30,50,40,40);
        button_FontItalic.setVisible(true);
        button_FontItalic.setBounds(90,50,40,40);
        lbFontSize = new JLabel("Font Size");
        //fontList.setSelectedIndex(17);
        fontList.addActionListener(this);

        tlBar.add(button_FontPlain);
        tlBar.add(button_FontBold);
        tlBar.add(button_FontItalic);
        tlBar.add(lbFontSize);
        tlBar.add(fontList);
        this.add(tlBar, BorderLayout.NORTH);

        button_FontPlain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontStyle = Font.PLAIN;
                font = new Font("Arial", fontStyle, fontSize);
                ta.setFont(font);
            }
        });
        button_FontBold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontStyle = Font.BOLD;
                font = new Font("Arial", fontStyle, fontSize);
                ta.setFont(font);
            }
        });

        button_FontItalic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontStyle = Font.ITALIC;
                font = new Font("Arial", fontStyle, fontSize);
                ta.setFont(font);
            }
        });

        fontList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontSize = (Integer) fontList.getSelectedItem();
                font = new Font("Arial", fontStyle, fontSize);
                ta.setFont(font);
            }
        });

        setMenuBar(mb);
        Menu m1 = new Menu("File");
        Menu m2 = new Menu("Edit");
        Menu m3 = new Menu("Tools");
        Menu m4 = new Menu("Help");
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(m4);
        MenuItem mi1[] = {
                new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save As"),
                new MenuItem("Page Setup"), new MenuItem("Print"), new MenuItem("Exit")
        };
        MenuItem mi2[] = { new MenuItem("Delete"), new MenuItem("Cut"),
                new MenuItem("Copy"), new MenuItem("Paste"), new MenuItem("Find"),
                new MenuItem("Find Next"), new MenuItem("Replace"),
                new MenuItem("Go To"), new MenuItem("Select All"),
                new MenuItem("Time Stamp") };
        MenuItem mi3[] = { new MenuItem("Choose Font"), new MenuItem("Compile"),
                new MenuItem("Run") };
        MenuItem mi4[] = { new MenuItem("Help Topics"),
                new MenuItem("About TextEditor") };
        for (int i = 0; i < mi1.length; i++) {
            m1.add(mi1[i]);
            mi1[i].addActionListener(this);
        }
        for (int i = 0; i < mi2.length; i++) {
            m2.add(mi2[i]);
            mi2[i].addActionListener(this);
        }
        m3.add(chkb);
        chkb.addActionListener(this);
        for (int i = 0; i < mi3.length; i++) {
            m3.add(mi3[i]);
            mi3[i].addActionListener(this);
        }
        for (int i = 0; i < mi4.length; i++) {
            m4.add(mi4[i]);
            mi4[i].addActionListener(this);
        }
        MyWindowsAdapter mw = new MyWindowsAdapter(this);
        addWindowListener(mw);
        setSize(500, 500);
        setTitle("Matt notepad");
        setVisible(true);

        //Add Scrolling to window
        JScrollPane scroll = new JScrollPane(ta);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//Sets the vertical scroll pane to always visible
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);//Sets the horizontal scroll pane to always visible
        add(scroll);

    }

    public void actionPerformed(ActionEvent ae) {
        String arg = (String) ae.getActionCommand();
        if (arg.equals("New")) {
            dispose();
            TextEditor t11 = new TextEditor();
            t11.setSize(500, 500);
            t11.setVisible(true);
        }
        try {
            if (arg.equals("Open")) {
                FileDialog fd1 = new FileDialog(this, "Select File", FileDialog.LOAD);
                fd1.setVisible(true);
                String s4 = "";
                s2 = fd1.getFile();
                s3 = fd1.getDirectory();
                s32 = s3 + s2;
                filePath = s32;
                File f = new File(s32);
                FileInputStream fii = new FileInputStream(f);
                len = (int) f.length();
                for (int j = 0; j < len; j++) {
                    char s5 = (char) fii.read();
                    s4 = s4 + s5;
                }
                ta.setText(s4);
                fii.close();
            }
        } catch (IOException e) {
        }
        try {
            if (arg.equals("Save As")) {
                FileDialog dialog1 = new FileDialog(this, "Save As", FileDialog.SAVE);
                dialog1.setVisible(true);
                s7 = dialog1.getDirectory();
                s8 = dialog1.getFile();
                s9 = s7 + s8 + ".txt";
                filePath = s9;
                s6 = ta.getText();
                len1 = s6.length();
                byte buf[] = s6.getBytes();
                File f1 = new File(s9);
                FileOutputStream fobj1 = new FileOutputStream(f1);
                for (int k = 0; k < len1; k++) {
                    fobj1.write(buf[k]);
                }
                fobj1.close();
            }
            this.setTitle(s8 + " TextEditor File");
        } catch (IOException e) {
        }
        if (arg.equals("Exit")) {
            System.exit(0);
        }
        if (arg.equals("Cut")) {
            str = ta.getSelectedText();
            i = ta.getText().indexOf(str);
            ta.replaceRange(" ", i, i + str.length());
        }
        if (arg.equals("Copy")) {
            str = ta.getSelectedText();
        }
        if (arg.equals("Paste")) {
            pos1 = ta.getCaretPosition();
            ta.insert(str, pos1);
        }
        if (arg.equals("Delete")) {
            String msg = ta.getSelectedText();
            i = ta.getText().indexOf(msg);
            ta.replaceRange(" ", i, i + msg.length());
            msg = "";
        }
        if (arg.equals("Select All")) {
            String strText = ta.getText();
            int strLen = strText.length();
            ta.select(0, strLen);
        }
        if (arg.equals("Time Stamp")) {
            GregorianCalendar gcalendar = new GregorianCalendar();
            String h = String.valueOf(gcalendar.get(Calendar.HOUR));
            String m = String.valueOf(gcalendar.get(Calendar.MINUTE));
            String s = String.valueOf(gcalendar.get(Calendar.SECOND));
            String date = String.valueOf(gcalendar.get(Calendar.DATE));
            String mon = months[gcalendar.get(Calendar.MONTH)];
            String year = String.valueOf(gcalendar.get(Calendar.YEAR));
            String hms = "Time" + " - " + h + ":" + m + ":" + s + " Date" + " - " + date + " " + mon + " " + year + " ";
            int loc = ta.getCaretPosition();
            ta.insert(hms, loc);
        }
        if (arg.equals("About TextEditor")) {
            AboutDialog d1 = new AboutDialog(this, "About TextEditor");
            d1.setVisible(true);
            setSize(500, 500);
        }
        if(arg.equals("Help Topics")){
            HelpDialog h1 = new HelpDialog(this, "Help");
            h1.setVisible(true);
            setSize(500, 500);
        }
        if(arg.equals("Print")){//This is for the new print button and how to add it
            try {
                ta.print();
            } catch (PrinterException e) {
                throw new RuntimeException(e);
            }
        }
        if(arg.equals("Save")){
            s6 = ta.getText();
            len1 = s6.length();
            byte buf[] = s6.getBytes();
            if(!filePath.isEmpty()){
                File f1 = new File(filePath);//This sets the file path, came from Open and Save As
                FileOutputStream fobj1 = null;//This can also become its own method for ease of use
                try {                         //Try Catches everywhere for this
                    fobj1 = new FileOutputStream(f1);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for (int k = 0; k < len1; k++) {
                    try {
                        fobj1.write(buf[k]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    fobj1.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(arg.equals("Page Setup")){
            // Display a dialog to allow the user to choose orientation
            Dialog pageSetupDialog = new Dialog(this, "Page Setup", true);
            pageSetupDialog.setLayout(new FlowLayout());

            // Create radio buttons for portrait and landscape orientations
            CheckboxGroup orientationGroup = new CheckboxGroup();
            Checkbox portraitCheckbox = new Checkbox("Portrait", orientationGroup, true);
            Checkbox landscapeCheckbox = new Checkbox("Landscape", orientationGroup, false);
            pageSetupDialog.add(portraitCheckbox);
            pageSetupDialog.add(landscapeCheckbox);

            // Add a button to apply the chosen orientation
            Button applyButton = new Button("Apply");
            applyButton.addActionListener(event -> {
                // Determine the chosen orientation and adjust the text editor accordingly
                if (portraitCheckbox.getState()) {
                    setSize(500, 500); // Adjust size for portrait orientation
                } else {
                    setSize(700, 400); // Adjust size for landscape orientation
                }
                pageSetupDialog.dispose(); // Close the dialog
            });
            pageSetupDialog.add(applyButton);

            // Set dialog size and display it
            pageSetupDialog.setSize(300, 100);
            pageSetupDialog.setVisible(true);
        }
        if(arg.equals("Find")){//Need to finish
            strFind = JOptionPane.showInputDialog(ta,"Enter the text to find", null);
            i = ta.getText().indexOf(strFind);

            if (i >= 0)
            {
                try
                {
                    highlighter.addHighlight(i, i + strFind.length(), painter);
                }
                catch (BadLocationException e)
                {
                    throw new RuntimeException(e);
                }
            }

        }
        if(arg.equals("FindAll")){//Need to finish
            List<Integer> listArray  = new ArrayList<Integer>();
            int index = 0;

            strFind = JOptionPane.showInputDialog(ta,"Enter the text to find all", null);
            i = ta.getText().indexOf(strFind);

            for (String line : ta.getText().split("\\n"))
            {
                String[] tokens = line.split(" ");
                for (int j = 0; j < tokens.length; j++)
                {
                    if (tokens[j].equals(strFind))
                    {
                        listArray.add(index);
                    }
                    index = index + tokens[j].length()+1;
                }
            }

            for (Integer k : listArray)
            {
                if (k >= 0) {
                    try
                    {
                        highlighter.addHighlight(k, k + strFind.length(), painter);
                    }
                    catch (BadLocationException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
        if(arg.equals("Replace")){//Need to finish
            strFind = JOptionPane.showInputDialog(ta,"Enter the text to find", null);
            strReplace = JOptionPane.showInputDialog(ta,"Enter the text to Replace", null);

            i = ta.getText().indexOf(strFind);

            if (i >= 0)
            {
                ta.replaceRange(strReplace, i, i + strFind.length());
            }
        }

    }
    public static void main(String args[]) {
        TextEditor to = new TextEditor();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String arg = (String) e.getItem();
        if(arg.equals("Word Wrap")){
            if(chkb.getState()){
                ta.setLineWrap(true);
                ta.setWrapStyleWord(true);
            }else{
                ta.setLineWrap(false);
                ta.setWrapStyleWord(false);
            }
        }

    }
}

class MyWindowsAdapter extends WindowAdapter {
    TextEditor tt;

    public MyWindowsAdapter(TextEditor ttt) {
        tt = ttt;
    }

    public void windowClosing(WindowEvent we) {
        tt.dispose();
    }
}

class AboutDialog extends Dialog implements ActionListener {
    AboutDialog(Frame parent, String title) {
        super(parent, title, false);
        this.setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(500, 300);
        setLayout(new BorderLayout());//Created to keep close button around the border of the JText area, aesthetics only
        JTextArea jta = new JTextArea();//Creates and adds text area to display about text
        jta.setEditable(false);//Make it so the JText are cant be edited
        jta.append("TextEditor is a simple text editing application.\n");
        jta.append("It provides basic functionality such as creating,\n");
        jta.append("opening, saving, and printing text files.\n");
        jta.append("It also includes features like cut, copy, paste, find, replace, and more.\n");
        add(jta, BorderLayout.CENTER);//Sets text to the center of JText area
        JButton closeButton = new JButton("Close");//Creates a close button to close dialog box
        closeButton.addActionListener(this);//This performs the close method
        add(closeButton, BorderLayout.SOUTH);//This sets the close button to the bottom of the page
    }


    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}

class HelpDialog extends Dialog implements ActionListener {
    HelpDialog(Frame parent, String title) {
        super(parent, title, false);
        this.setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setLayout(new BorderLayout());//Created to keep close button around the border of the JText area, aesthetics only
        setSize(500, 300);
        JTextArea jta = new JTextArea();//Creates and adds text area to display about text
        JScrollPane scrollPane = new JScrollPane(jta);//Creates the instance of Scroll to be used
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//Sets the vertical scroll pane to always visible
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);//Sets the horizontal scroll pane to always visible

        jta.setEditable(false);//Make it so the JText are cant be edited
        jta.append("Welcome to the TextEditor Help!\n\n");
        jta.append("File Menu:\n");
        jta.append("- New: Create a new file\n");
        jta.append("- Open: Open an existing file\n");
        jta.append("- Save: Save the current file\n");
        jta.append("- Save As: Save the current file with a different name\n");
        jta.append("- Page Setup: Configure page settings for printing\n");
        jta.append("- Print: Print the current file\n");
        jta.append("- Exit: Exit the TextEditor\n\n");
        jta.append("Edit Menu:\n");
        jta.append("- Cut: Cut the selected text\n");
        jta.append("- Copy: Copy the selected text\n");
        jta.append("- Paste: Paste the copied or cut text\n");
        jta.append("- Delete: Delete the selected text\n");
        jta.append("- Select All: Select all text in the editor\n");
        jta.append("- Time Stamp: Insert a time stamp at the current cursor position\n");
        jta.append("- About TextEditor: Display information about the TextEditor\n");
        jta.append("- Help Topics: Display help information about the TextEditor\n");
        JButton closeButton = new JButton("Close");//Creates a close button to close dialog box
        closeButton.addActionListener(this);//This performs the close method
        add(closeButton, BorderLayout.SOUTH);//This sets the close button to the bottom of the page
        add(jta);//Sets text to the center of JText area
        //add(scrollPane);
    }


    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}
