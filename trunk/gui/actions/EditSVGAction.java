package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import svgedit.gui.EditBox;
import svgedit.gui.Frame;

public class EditSVGAction extends AbstractAction{

	private Frame frame;
	public EditSVGAction(Frame frame){
		super("Edit SVG");
	    this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		frame.startEditBox();
		System.out.println("EDIT SVG");
		
		
	}
}
