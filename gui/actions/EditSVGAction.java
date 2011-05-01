package svgedit.gui.actions;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import svgedit.gui.EditBox;
import svgedit.gui.Frame;
import svgedit.select.selectMouseListener;

public class EditSVGAction extends AbstractAction{

	private Frame frame;
	private selectMouseListener sml;
	public EditSVGAction(Frame frame){
		super("Edit SVG");
	    this.frame = frame;
	    sml = new selectMouseListener(frame);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		frame.startEditBox();
		frame.getView().clearListener();
		frame.setCursor(Cursor.DEFAULT_CURSOR);
		
		frame.getView().addMouseListener(sml.getListener());
		frame.getView().addMouseMotionListener(sml.getListener());
		
		System.out.println("EDIT SVG");
		
		
	}
}
