package svgedit.gui;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

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
	    JMenu menu = new JMenu("Menu");	
		add(menu);
		menu.add(new JMenuItem(mainMenuAction));
		menu.add(new JMenuItem(selectAllAction));
		menu.add(new JMenuItem(groupAction));
		menu.add(new JMenuItem(ungroupAction));
		menu.add(new JMenuItem(deleteAction));
	
	}
	
	
}
