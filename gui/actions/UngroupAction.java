package svgedit.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import svgedit.gui.Frame;

public class UngroupAction extends AbstractAction{
    
	private Frame frame;
	public UngroupAction(Frame frame){
		super("Ungroup");
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Ungroup");
	}

}
