package svgedit.gui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import svgedit.gui.actions.DeleteAction;
import svgedit.gui.actions.GroupAction;
import svgedit.gui.actions.MainMenuAction;
import svgedit.gui.actions.SelectAllAction;
import svgedit.gui.actions.UngroupAction;

public class SelectMenubar extends JMenuBar{

	private Frame frame;
	private Action mainMenuAction;
	private Action selectAllAction;
	private Action groupAction;
	private Action ungroupAction;
	private Action deleteAction;
	
	
	public SelectMenubar(Frame frame){
		this.frame = frame;
		mainMenuAction = new MainMenuAction(frame);
		selectAllAction = new SelectAllAction(frame);
		groupAction = new GroupAction(frame);
		ungroupAction = new UngroupAction(frame);
		deleteAction = new DeleteAction(frame);
		selectAllAction.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	       
	    JMenu menu = new JMenu("Menu");	
		add(menu);
		menu.add(new JMenuItem(mainMenuAction));
		menu.add(new JMenuItem(selectAllAction));
		menu.add(new JMenuItem(groupAction));
		menu.add(new JMenuItem(ungroupAction));
		menu.add(new JMenuItem(deleteAction));
	
	}
	
	
}
