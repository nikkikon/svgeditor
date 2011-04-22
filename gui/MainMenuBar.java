package svgedit.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import svgedit.gui.actions.DocumentsPropertyAction;
import svgedit.gui.actions.EditSVGAction;
import svgedit.gui.actions.InsertCircleAction;
import svgedit.gui.actions.InsertLineAction;
import svgedit.gui.actions.InsertRectangleAction;
import svgedit.gui.actions.NewAction;
import svgedit.gui.actions.OpenDocumentAction;
import svgedit.gui.actions.QuitAction;
import svgedit.gui.actions.SaveAction;
import svgedit.gui.actions.SaveAsAction;

public class MainMenuBar extends JMenuBar{
	private Action openAction;
    private Action quitAction;
    private Action newAction;
    private Action saveAction;
    private Action save_asAction;
    private Action document_propertiesAction;
    private Action insert_rectangleAction;
    private Action insert_circleAction;
    private Action insert_lineAction;
    private Action editsvg;
    
    private Frame frame;
    public MainMenuBar(Frame frame){
    	this.frame = frame;
    	
    	openAction = new OpenDocumentAction(frame);
        quitAction = new QuitAction(frame);
        //------------------------------------------------
        newAction = new NewAction(frame);
        saveAction = new SaveAction(frame);
        save_asAction = new SaveAsAction(frame);
        document_propertiesAction = new DocumentsPropertyAction(frame);
        
        insert_rectangleAction = new InsertRectangleAction(frame);
        insert_circleAction = new InsertCircleAction(frame);
        insert_lineAction = new InsertLineAction(frame);
        
        editsvg = new EditSVGAction(frame);
        //------------------------------------------------
        // Associate keyboard shortcuts with actions
        openAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        newAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        save_asAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK));

        // Create menus
        
        //jp.add(menuBar);
        
        
        JMenu fileMenu = new JMenu("File");
        JMenu insertMenu = new JMenu("Insert");
        JMenu editMenu = new JMenu("Edit");
        fileMenu.add(new JMenuItem(newAction));
        fileMenu.add(new JMenuItem(openAction));
        fileMenu.add(new JMenuItem(saveAction));
        fileMenu.add(new JMenuItem(save_asAction));
        fileMenu.add(new JMenuItem(document_propertiesAction));
        fileMenu.add(new JMenuItem(quitAction));
        
        //--------------------------------------------------------------
        insertMenu.add(new JMenuItem(insert_rectangleAction));
        insertMenu.add(new JMenuItem(insert_circleAction));
        insertMenu.add(new JMenuItem(insert_lineAction));
        editMenu.add(new JMenuItem(editsvg));
        add(fileMenu);
        add(insertMenu);
        add(editMenu);
    	
    }

}
