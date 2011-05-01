package svgedit.gui.actions;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;
import svgedit.insert.InsertLineListener;
import svgedit.insert.InsertListener;

public class InsertLineAction extends AbstractAction{

	private Frame frame;
	private InsertLineListener insertListener;
	public InsertLineAction(Frame frame){
		super("Insert Line");
	    this.frame = frame;
	    insertListener = new InsertLineListener(frame);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		frame.getView().clearListener();
		frame.setCursor(Cursor.CROSSHAIR_CURSOR);
		frame.getView().addMouseListener(insertListener.getListener());
		frame.getView().addMouseMotionListener(insertListener.getListener());
		//System.out.println("add listener");
		//frame.removeMouseListener(frame.getMouseListeners()[0]);
		//frame.removeMouseMotionListener(frame.getMouseMotionListeners()[0]);
		System.out.println("INSERT LINE");
	}
	
	
}
