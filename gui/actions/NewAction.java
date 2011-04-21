package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

public class NewAction extends AbstractAction{

	private Frame frame;
     
	public NewAction(Frame frame){
		super("New");
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		System.out.println("NEW FILE");
		
	}
	
}
