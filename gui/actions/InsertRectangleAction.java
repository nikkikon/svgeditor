package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

public class InsertRectangleAction extends AbstractAction{

	private Frame frame;
	
	public InsertRectangleAction(Frame frame){
		super("Insert Rectangle");
	    this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("INSERTRECTANGLE");
	}
}
