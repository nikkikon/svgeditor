package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

public class InsertCircleAction extends AbstractAction{

	private Frame frame;
	
	public InsertCircleAction(Frame frame){
		super("Insert Circle");
	    this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("INSERT CIRCLE");
	}
	
}
