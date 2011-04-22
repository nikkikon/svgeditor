package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

public class GroupAction extends AbstractAction{

	private Frame frame;
    public GroupAction(Frame frame){
    	super("Group");
    	this.frame = frame;
    	
    }
	 @Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Group");
	}

}
