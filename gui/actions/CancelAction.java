package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;

import svgedit.gui.DocumentPropertyEditBox;
import svgedit.gui.Frame;
import svgedit.gui.NewFile;


public class CancelAction extends AbstractAction{

	private JFrame frame;
	
	
	public CancelAction(DocumentPropertyEditBox DocumentPropertyEditBox){
		super("Cancel");
		this.frame = DocumentPropertyEditBox;
	}
	
	public CancelAction(NewFile newFile){
		super("Cancel");
		this.frame = newFile;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		frame.dispose();
	}
       
}
