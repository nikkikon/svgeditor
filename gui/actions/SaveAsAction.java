package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

public class SaveAsAction extends AbstractAction{

	private Frame frame;
	public SaveAsAction(Frame frame){
		super("Save As");
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("SAVE AS");
	}

}
