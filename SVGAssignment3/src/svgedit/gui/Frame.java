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

import javax.swing.AbstractAction;
import javax.swing.Action;
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
import svgedit.gui.actions.DeleteAction;
import svgedit.gui.actions.EditDocumentPropertiesAction;
import svgedit.gui.actions.GroupAction;
import svgedit.gui.actions.InsertCircleElementAction;
import svgedit.gui.actions.InsertLineElementAction;
import svgedit.gui.actions.InsertRectElementAction;
import svgedit.gui.actions.NewDocumentAction;
import svgedit.gui.actions.OpenDocumentAction;
import svgedit.gui.actions.QuitAction;
import svgedit.gui.actions.SaveDocumentAction;
import svgedit.gui.actions.SaveDocumentAsAction;
import svgedit.gui.actions.SelectAllAction;
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

    // Actions for adding new elements to the document */
    private Action insertRectAction;
    private Action insertCircleAction;
    private Action insertLineAction;
    private JToggleButton[] toolBarButtons;

    private PaintDropDown fillColorPicker;
    private PaintDropDown strokeColorPicker;
    private JTextField strokeWidthTextField;

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

        selectAllAction = new SelectAllAction(this);
        groupAction = new GroupAction(this);
        ungroupAction = new UngroupAction(this);
        deleteAction = new DeleteAction(this);

        insertRectAction = new InsertRectElementAction(this);
        insertCircleAction = new InsertCircleElementAction(this);
        insertLineAction = new InsertLineElementAction(this);

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
        fileMenu.add(new JMenuItem(newAction));
        fileMenu.add(new JMenuItem(openAction));
        fileMenu.add(new JMenuItem(saveAction));
        fileMenu.add(new JMenuItem(saveAsAction));
        fileMenu.add(new JMenuItem(documentPropertiesAction));
        fileMenu.add(new JMenuItem(quitAction));
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(new JMenuItem(selectAllAction));
        editMenu.add(new JMenuItem(groupAction));
        editMenu.add(new JMenuItem(ungroupAction));
        editMenu.add(new JMenuItem(deleteAction));
        menuBar.add(editMenu);

        JMenu insertMenu = new JMenu("Insert");
        insertMenu.add(new JMenuItem(insertRectAction));
        insertMenu.add(new JMenuItem(insertCircleAction));
        insertMenu.add(new JMenuItem(insertLineAction));
        menuBar.add(insertMenu);

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
            public void documentChanged() {
                try {
                    SVGLength length = new SVGLength();
                    length.setValueFromString(strokeWidthTextField.getText());
                    view.setSelectedStrokeWidth(length);
                    view.getDefaultStyle().getStrokeWidth().setValueFromLength(length);
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
        toolBar.add(newAction);
        toolBar.add(openAction);
        toolBar.add(saveAction);
        toolBar.add(new JToolBar.Separator());
        for (JToggleButton button : toolBarButtons)
            toolBar.add(button);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(new JLabel("Fill:"));
        toolBar.add(fillColorPicker);
        toolBar.add(new JLabel("Stroke:"));
        toolBar.add(strokeColorPicker);
        toolBar.add(new JLabel("Stroke Width:"));
        toolBar.add(strokeWidthTextField);

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
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage(), "Unable to open file", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage(), "Unable to save file", JOptionPane.ERROR_MESSAGE);
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
            int result = JOptionPane.showConfirmDialog(null, "Save changes to current document?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

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
}
