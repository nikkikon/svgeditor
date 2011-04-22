package svgedit.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import svgedit.gui.Frame;

public class SelectAllAction extends AbstractAction{

	private Frame frame;
	public SelectAllAction(Frame frame){
		super("Select All");
	    this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Select All");
	}

}
