package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import svgedit.gui.DocumentPropertyEditBox;
import svgedit.gui.Frame;

public class DocumentsPropertyAction extends AbstractAction{

	private Frame frame;
	
	public DocumentsPropertyAction(Frame frame){
		super("Document Properties");
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("DOCUMENT PROPERTIES");
		DocumentPropertyEditBox dpeb = new DocumentPropertyEditBox(frame);
		
		
	}
	
	
}
