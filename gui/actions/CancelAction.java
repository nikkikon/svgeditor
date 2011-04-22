package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import svgedit.gui.DocumentPropertyEditBox;
import svgedit.gui.Frame;


public class CancelAction extends AbstractAction{

	private DocumentPropertyEditBox frame;
	
	public CancelAction(DocumentPropertyEditBox DocumentPropertyEditBox){
		super("Cancel");
		this.frame = DocumentPropertyEditBox;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		frame.dispose();
	}
       
}
