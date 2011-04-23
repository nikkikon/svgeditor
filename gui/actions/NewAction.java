package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import svgedit.gui.Frame;
import svgedit.gui.NewFile;

public class NewAction extends AbstractAction{

	private Frame frame;
   
    private boolean issaved;
    private NewFile newFile;
	public NewAction(Frame frame){
		super("New");
		this.frame = frame;
		issaved = false;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		System.out.println("NEW FILE");
		newFile = new NewFile(frame);
		
	}
}
