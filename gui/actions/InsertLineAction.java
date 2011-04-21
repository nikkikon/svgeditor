package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

public class InsertLineAction extends AbstractAction{

	private Frame frame;
	public InsertLineAction(Frame frame){
		super("Insert Line");
	    this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("INSERT LINE");
	}
	
}
