package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import svgedit.gui.DocumentPropertyEditBox;
import svgedit.gui.Frame;

public class ConfirmAction extends AbstractAction{

	private DocumentPropertyEditBox frame;
	
	public ConfirmAction(DocumentPropertyEditBox documentPropertyEditBox){
		super("Confirm");
		this.frame = documentPropertyEditBox;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("ConfirmAction");
	}
	
	
	
}
