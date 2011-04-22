package svgedit.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import svgedit.gui.Frame;

public class DeleteAction extends AbstractAction{

	private Frame frame;
	public DeleteAction(Frame frame){
		super("Delete");
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("DELETE");
	}

}
