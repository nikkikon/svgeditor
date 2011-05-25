package svgedit.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import Test.TestUnit;

import svgedit.commandManager.FillColorChangeCommand;
import svgedit.commandManager.CommandStack;
import svgedit.gui.actions.DeleteAction;
import svgedit.gui.actions.EditDocumentPropertiesAction;
import svgedit.gui.actions.EnglishAction;
import svgedit.gui.actions.GemanAction;
import svgedit.gui.actions.GroupAction;
import svgedit.gui.actions.InsertCircleElementAction;
import svgedit.gui.actions.InsertLineElementAction;
import svgedit.gui.actions.InsertRectElementAction;
import svgedit.gui.actions.JapaneseAction;
import svgedit.gui.actions.NewDocumentAction;
import svgedit.gui.actions.OpenDocumentAction;
import svgedit.gui.actions.QuitAction;
import svgedit.gui.actions.RedoAction;
import svgedit.gui.actions.SaveDocumentAction;
import svgedit.gui.actions.SaveDocumentAsAction;
import svgedit.gui.actions.SelectAllAction;
import svgedit.gui.actions.UndoAction;
import svgedit.gui.actions.UngroupAction;

import svgedit.svg.SVGLength;
import svgedit.svg.SVGPaint;
import svgedit.svg.SVGStylable;
import svgedit.svg.SVGDocument;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;
import svgedit.xml.*;

/** Displays a window containing a SVG view, toolbar and menu. */
@SuppressWarnings("serial")
public class Frame extends JFrame {

    /** User preferences */
    private Preferences preferences;

    /** The document displayed by the frame */
    private SVGDocument document;

    /** An SVG view of the current document */
    private View view;

    // Actions available from the menu and toolbar */
    private Action newAction;
    private Action openAction;
    private Action saveAction;
    private Action saveAsAction;
    private Action documentPropertiesAction;
    private Action quitAction;

    // Actions for modifying the current selection */
    private Action selectAllAction;
    private Action groupAction;
    private Action ungroupAction;
    private Action deleteAction;
    private Action undoAction;
    private Action redoAction;

    // Actions for adding new elements to the document */
    private Action insertRectAction;
    private Action insertCircleAction;
    private Action insertLineAction;
    private Action englishAction;
    private Action gemanAction;
    private Action japaneseAction;
    
    
    private JToggleButton[] toolBarButtons;
    private JToggleButton[] toolBarButtonsReplica;

    private PaintDropDown fillColorPicker;
    private PaintDropDown strokeColorPicker;
    private JTextField strokeWidthTextField;

    private JMenu[] jm = new JMenu[4];
    private JMenuItem[] jmi = new JMenuItem[16];
    private JLabel[] jl = new JLabel[3];
    
	public ResourceBundle rb;
	private String saveChangesConfirm="Save changes to current document?";
	private String saveChangesWindow="Save Changes?";
	private String unableOpen="Unable to open file";
	private String unableSave="Unable to save file";
	private String str;

    
    private String language = "en";
    private Locale[]   alSupported = {
            Locale.US,
            Locale.GERMAN,
            Locale.JAPAN
               };
   
    private FillColorChangeCommand colorChangeCommand;
    
    
    private class FrameWindowListener extends WindowAdapter {

        /** Invokes quit action when user attempts to close window */
        @Override
        public void windowClosing(WindowEvent we) {
            quitAction.actionPerformed(null);
        }
    }

    /** Creates a frame with a new, empty document. */
    public Frame() {
        preferences = new Preferences();

        // Create all actions presented by the menu bar and toolbar

        newAction = new NewDocumentAction(this);
        openAction = new OpenDocumentAction(this);
        saveAction = new SaveDocumentAction(this);
        saveAsAction = new SaveDocumentAsAction(this);
        documentPropertiesAction = new EditDocumentPropertiesAction(this);
        quitAction = new QuitAction(this);

        undoAction = new UndoAction(this);
        redoAction = new RedoAction(this);
        selectAllAction = new SelectAllAction(this);
        groupAction = new GroupAction(this);
        ungroupAction = new UngroupAction(this);
        deleteAction = new DeleteAction(this);

        insertRectAction = new InsertRectElementAction(this);
        insertCircleAction = new InsertCircleElementAction(this);
        insertLineAction = new InsertLineElementAction(this);
        
        
        englishAction = new EnglishAction(this);
        gemanAction = new GemanAction(this);
        japaneseAction = new JapaneseAction(this);

       
        
        
        // Associate keyboard shortcuts with actions

        newAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        openAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveAsAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        selectAllAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        groupAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        ungroupAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        // Hook up handler to window close event to display save confirmation dialog.

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new FrameWindowListener());

        // Create menus

        JMenuBar menuBar = new JMenuBar();
        add(menuBar, BorderLayout.NORTH);

        JMenu fileMenu = new JMenu("File");
        JMenuItem newActionJMenuItem = new JMenuItem(newAction);
        JMenuItem openActionJMenuItem = new JMenuItem(openAction);
        JMenuItem saveActionJMenuItem = new JMenuItem(saveAction);
        JMenuItem saveAsActionJMenuItem = new JMenuItem(saveAsAction);
        JMenuItem documentPropertiesActionJMenuItem = new JMenuItem(documentPropertiesAction);
        JMenuItem quitActionJMenuItem = new JMenuItem(quitAction);
        
        fileMenu.add(newActionJMenuItem);
        fileMenu.add(openActionJMenuItem);
        fileMenu.add(saveActionJMenuItem);
        fileMenu.add(saveAsActionJMenuItem);
        fileMenu.add(documentPropertiesActionJMenuItem);
        fileMenu.add(quitActionJMenuItem);
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoActionJMenuItem =  new JMenuItem(undoAction);
        JMenuItem redoActionJMenuItem = new JMenuItem(redoAction);
        JMenuItem selectAllActionJMenuItem =  new JMenuItem(selectAllAction);
        JMenuItem groupActionJMenuItem =  new JMenuItem(groupAction);
        JMenuItem ungroupActionJMenuItem =  new JMenuItem(ungroupAction);
        JMenuItem deleteActionJMenuItem =  new JMenuItem(deleteAction);
   
        editMenu.add(undoActionJMenuItem);
        editMenu.add(redoActionJMenuItem);
        editMenu.add(selectAllActionJMenuItem);
        editMenu.add(groupActionJMenuItem);
        editMenu.add(ungroupActionJMenuItem);
        editMenu.add(deleteActionJMenuItem);
        
        menuBar.add(editMenu);

        JMenu insertMenu = new JMenu("Insert");
        JMenuItem insertRectActionJMenuItem = new JMenuItem(insertRectAction);
        JMenuItem insertCircleActionJMenuItem = new JMenuItem(insertCircleAction);
        JMenuItem insertLineActionJMenuItem = new JMenuItem(insertLineAction);
        
        insertMenu.add(insertRectActionJMenuItem);
        insertMenu.add(insertCircleActionJMenuItem);
        insertMenu.add(insertLineActionJMenuItem);
        menuBar.add(insertMenu);
        
        JMenu LanguageMenu = new JMenu("Language");
        JMenuItem englishActionJMenuItem =new JMenuItem(englishAction);
        JMenuItem gemanActionJMenuItem =new JMenuItem(gemanAction);
        JMenuItem japaneseActionJMenuItem =new JMenuItem(japaneseAction);
        
        LanguageMenu.add(englishActionJMenuItem);
        LanguageMenu.add(gemanActionJMenuItem);
        LanguageMenu.add(japaneseActionJMenuItem);
        fileMenu.add(LanguageMenu);
        jmi[0] = newActionJMenuItem;
        jmi[1] = openActionJMenuItem;
        jmi[2] = saveActionJMenuItem;
        jmi[3] = saveAsActionJMenuItem;
        jmi[4] = documentPropertiesActionJMenuItem;
        jmi[5] = quitActionJMenuItem;
        jmi[6] = selectAllActionJMenuItem;
        jmi[7] = groupActionJMenuItem;
        jmi[8] = ungroupActionJMenuItem;
        jmi[9] = deleteActionJMenuItem;
        jmi[10] = insertRectActionJMenuItem;
        jmi[11] = insertCircleActionJMenuItem;
        jmi[12] = insertLineActionJMenuItem;
        jmi[13] = englishActionJMenuItem;
        jmi[14] = gemanActionJMenuItem;
        jmi[15] = japaneseActionJMenuItem;
        
        jm[0] = LanguageMenu;
        jm[1] = fileMenu;
        jm[2] = editMenu;
        jm[3] = insertMenu;
        
        // Create toolbar


       
        fillColorPicker = new PaintDropDown(PaintDropDown.PAINT_ATTRIBUTE_FILL);       
        fillColorPicker.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {               
                SVGPaint paint = fillColorPicker.getPaint();
                view.setSelectedFill(paint);
                view.getDefaultStyle().getFill().setValueFromPaint(paint);               
            }
        });
        

        strokeColorPicker = new PaintDropDown(PaintDropDown.PAINT_ATTRIBUTE_STROKE);
        strokeColorPicker.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                SVGPaint paint = strokeColorPicker.getPaint();
                view.setSelectedStroke(paint);
                view.getDefaultStyle().getStroke().setValueFromPaint(paint);
            }
        });
        

        strokeWidthTextField = new JTextField();
        strokeWidthTextField.getDocument().addDocumentListener(new DocumentChangedAdapter() {

            @Override
            public void documentChanged(){
                try {
                	System.out.println("Document Changed Start");
                    SVGLength length = new SVGLength();
                    length.setValueFromString(strokeWidthTextField.getText());
                    if(strokeWidthTextField.hasFocus()){
                    	view.setSelectedStrokeWidth(length);
                    }
                    //view.setSelectedStrokeWidth(length);
                    view.getDefaultStyle().getStrokeWidth().setValueFromLength(length);
                    System.out.println("Document Changed End");
                } catch (NumberFormatException e) {
                    // Ignore parse error; the user may still be typing
                }
            }

        });
        strokeWidthTextField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent fe) {
                // If invalid text was left in the text field, restore the
                // text to the current selection
                view.setSelectedElements(view.getSelectedElements());
            }

        });

        toolBarButtons = new JToggleButton[] { new JToggleButton(insertRectAction), new JToggleButton(insertCircleAction), new JToggleButton(insertLineAction) };

        JToolBar toolBar = new JToolBar();
        toolBar.add(newActionJMenuItem);
        toolBar.add(openActionJMenuItem);
        toolBar.add(saveActionJMenuItem);
        toolBar.add(new JToolBar.Separator());
        for (JToggleButton button : toolBarButtons)
            toolBar.add(button);
        toolBar.add(new JToolBar.Separator());
        JLabel fillLabel = new JLabel("Fill:");
        JLabel strokeLabel = new JLabel("Stroke:");
        JLabel strokeWidthLabel = new JLabel("Stroke Width:");
        toolBar.add(fillLabel);
        toolBar.add(fillColorPicker);
        toolBar.add(strokeLabel);
        toolBar.add(strokeColorPicker);
        toolBar.add(strokeWidthLabel);
        toolBar.add(strokeWidthTextField);
        
        jl[0] = fillLabel;
        jl[1] = strokeLabel;
        jl[2] = strokeWidthLabel;
        
        JPanel viewContainer = new JPanel();
        viewContainer.setLayout(new BorderLayout());
        add(viewContainer);

        viewContainer.add(toolBar, BorderLayout.NORTH);

        // Create view

        view = new View();
        view.addViewListener(new ViewListener() {
            public void viewSelectionChanged(View view) {
                selectionChanged();
            }

            public void viewManipulatorChanged(View view) {
                setManipulatorAction(null);
            }
        });
        viewContainer.add(view);

        // Map delete and backspace keys to delete action
        view.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
        view.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "delete");
        view.getActionMap().put("delete", deleteAction);

        // Always start with blank canvas
        newFile();

        view.setSelectionManipulator();

        // Set an initial view size in case the document size is based on the view
        view.setSize(new Dimension(480, 360));

        // Size the view to the document
        view.setPreferredSize(new Dimension((int) document.getWidth().getValue() + 20, (int) document.getHeight().getValue() + 20));
        pack();
        
        toolBarButtonsReplica = new JToggleButton[] { new JToggleButton(insertRectAction), new JToggleButton(insertCircleAction), new JToggleButton(insertLineAction) };
        
        
    }

    /** Gets the preferences object to use for getting and setting user defaults.
     *
     * @return the preferences object associated with this frame
     */
    public Preferences getPreferences() {
        return preferences;
    }

    /** Gets the document currently displayed in the frame.
     *
     * @return an SVG document
     */
    public SVGDocument getDocument() {
        return document;
    }

    /** Sets the document currently displayed in the frame.
     *
     * @param document the document to display
     * @param file the file the document was loaded from, or null if it's a new file
     */
    private void setDocument(SVGDocument document, File file) {
        this.document = document;
        view.setDocument(document);
        setFile(file);
    }

    /** Gets the view presenting the document for this frame. */
    public View getView() {
        return view;
    }

    /** Sets the action responsible for the view's current manipulator.  This
     *  causes that action's toolbar button to appear selected, and for all
     *  other toolbar buttons to unselect.
     *
     * @param action the action that set the view's manipulator
     */
    public final void setManipulatorAction(Action action) {
        for (JToggleButton button : toolBarButtons) {
            button.setSelected(button.getAction() == action);
        }
    }

    /**
     * Set the filename of the current document. Sets the frame's title and
     * enables the appropriate save menu items.
     * 
     * @param file
     *            the filename of the current document, or null if the document
     *            is new and has no filename
     */
    private void setFile(File file) {
        document.setFile(file);

        if (file != null)
            setTitle(file.getName() + " - SVGEdit");
        else
            setTitle("SVGEdit");
    }

    /** Sets the document to a new document with no associated file. */
    public final void newFile() {
        SVGDocument doc = new SVGDocument(view);
        setDocument(doc, null);
    }

    /** Loads a file and sets it as the current document.  Displays an error
     *  message and leaves the current document unchanged if the document cannot
     *  be opened.
     *
     * @param file the file to load
     */
    public void openFile(File file) {
        try {
            SVGDocument doc = XMLReader.read(file, view);
            setDocument(doc, file);
        } catch (IOException e) {
        	if(getLanguage().equals("ge")){
        		rb = ResourceBundle.getBundle( 
                        "ge", 
                        getLocales()[1]);
        		restoreAllPopup();
        		str = unableOpen.trim().replaceAll(" ", "");
        		unableOpen=rb.getString(str);
        	}
        	if(getLanguage().equals("en")){
        		restoreAllPopup();
        	}if(getLanguage().equals("jp")){
        		rb = ResourceBundle.getBundle( 
                        "jp", 
                        getLocales()[2]);
        		restoreAllPopup();
        		str = unableOpen.trim().replaceAll(" ", "");
        		unableOpen=rb.getString(str);
        	}
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage(), unableOpen, JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Writes the current document to the given file.
     *
     * @param file the file to save to
     */
    public void saveFile(File file) {
        assert document != null;
        assert file != null;

        try {
            XMLWriter.write(document, file);
            setFile(file);
            document.setModified(false);
        } catch (IOException e) {
        	if(getLanguage().equals("ge")){
        		rb = ResourceBundle.getBundle( 
                        "ge", 
                        getLocales()[1]);
        		restoreAllPopup();
        		str = unableSave.trim().replaceAll(" ", "");
        		unableSave=rb.getString(str);
        	}
        	if(getLanguage().equals("en")){
        		restoreAllPopup();
        	}if(getLanguage().equals("jp")){
        		rb = ResourceBundle.getBundle( 
                        "jp", 
                        getLocales()[2]);
        		restoreAllPopup();
        		str = unableSave.trim().replaceAll(" ", "");
        		unableSave=rb.getString(str);
        	}
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage(), unableSave, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * If the document has unsaved modifications, prompt the user to save
     * changes.
     * 
     * @return false if the user pressed 'Cancel' at the dialog, otherwise true
     */
    public boolean confirmSaveChanges() {
        if (document.getModified()) {
        	if(getLanguage().equals("ge")){
        		rb = ResourceBundle.getBundle( 
                        "ge", 
                        getLocales()[1]);
        		restoreAllPopup();
        		str = unableSave.trim().replaceAll(" ", "");
        		saveChangesConfirm=rb.getString(str);
        		str = unableSave.trim().replaceAll(" ", "");
        		saveChangesWindow=rb.getString(str);
        	}
        	if(getLanguage().equals("en")){
        		restoreAllPopup();
        	}if(getLanguage().equals("jp")){
        		rb = ResourceBundle.getBundle( 
                        "jp", 
                        getLocales()[2]);
        		restoreAllPopup();
        		str = unableSave.trim().replaceAll(" ", "");
        		saveChangesConfirm=rb.getString(str);
        		str = unableSave.trim().replaceAll(" ", "");
        		saveChangesWindow=rb.getString(str);
        	}
            int result = JOptionPane.showConfirmDialog(null, saveChangesConfirm, saveChangesWindow, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if (result == JOptionPane.CANCEL_OPTION)
                return false;

            if (result == JOptionPane.OK_OPTION) {
                if (document.getFile() != null)
                    saveAction.actionPerformed(null);
                else
                    saveAsAction.actionPerformed(null);
            }
        }

        return true;
    }

    /** Handler called when the view's selection changes.  Updates the toolbar
     *  elements to display the properties of the current selection, and enables
     *  relevant actions for the current selection.
     */
    private void selectionChanged() {
        SVGElement[] selectedElements = view.getSelectedElements();

        SVGStylable style;
        if (selectedElements.length != 0)
            style = view.getSelectionStyle();
        else
            style = view.getDefaultStyle();

        fillColorPicker.setPaint(style.getFill());
        strokeColorPicker.setPaint(style.getStroke());
        if (style.getStrokeWidth() != null)
            strokeWidthTextField.setText(style.getStrokeWidth().valueAsString());
        else
            strokeWidthTextField.setText("");

        // Enable delete action if elements are selected
        deleteAction.setEnabled(selectedElements.length > 0);

        // Enable group action if more than one element is selected
        groupAction.setEnabled(selectedElements.length > 1);

        // Enable ungroup action if a group is selected
        ungroupAction.setEnabled(false);
        for (SVGElement elem : selectedElements) {
            if (elem instanceof SVGGroup)
                ungroupAction.setEnabled(true);
        }
    }
    
    
    public void restoreAllMenu(){
    	jmi[0].setText("New");
        jmi[1].setText("Open...");
        jmi[2].setText("Save");
        jmi[3].setText("Save As...");
        jmi[4].setText("Document Properties...");
        jmi[5].setText("Quit");
        jmi[6].setText("Select All");
        jmi[7].setText("Group");
        jmi[8].setText("Ungroup");
        jmi[9].setText("Delete");
        jmi[10].setText("Rectangle");
        jmi[11].setText("Circle");
        jmi[12].setText("Line");
        jmi[13].setText("English");
        jmi[14].setText("German");
        jmi[15].setText("Japanese");
        jl[0].setText("Fill");
        jl[1].setText("Stroke");
        jl[2].setText("Stroke Width");
        jm[0].setText("Language");
        jm[1].setText("File");
        jm[2].setText("Edit");
        jm[3].setText("Insert");
        toolBarButtons[0].setText("Rectangle");
        toolBarButtons[1].setText("Circle");
        toolBarButtons[2].setText("Line");
    }
    
    public void setLanguage(String la){
    	this.language = la;
    }
    
    public String getLanguage(){
    	return this.language;
    }
    
    public JMenu[] getJMenus(){
    	return jm;
    }
    
    public JMenuItem[] getJMenuItems(){
    	return jmi;
    }
    
    public JLabel[] getJLabel(){
    	return jl;
    }
    public Locale[] getLocales(){
    	return alSupported;
    }
    
    public JToggleButton[] getJToggleButton(){
    	return toolBarButtons;
    }
    
    private void restoreAllPopup(){
    	saveChangesConfirm="Save changes to current document?";
    	saveChangesWindow="Save Changes?";
    	unableOpen="Unable to open file";
    	unableSave="Unable to save file";
    }
    


}
