package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

public class SaveAction extends AbstractAction{

	private Frame frame;
	public SaveAction(Frame frame){
		super("Save");
		this.frame = frame;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("SAVE FILE");
	}

}
