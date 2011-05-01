package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;


public class MainMenuAction extends AbstractAction{

	private Frame frame;
	
	public MainMenuAction(Frame frame){
		super("Main Menu");
		this.frame = frame;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Main menu");
	    frame.deleteSelectMenu();
	   // frame.closeEditBox();
	}
}
