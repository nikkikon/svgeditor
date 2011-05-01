package svgedit.gui.actions;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;
import svgedit.insert.InsertRectListener;
import svgedit.select.selectMouseListener;

public class InsertRectangleAction extends AbstractAction{

	private Frame frame;
	//private selectMouseListener sml;
	private InsertRectListener insertListener;
	public InsertRectangleAction(Frame frame){
		super("Insert Rectangle");
	    this.frame = frame;
	    insertListener = new InsertRectListener(frame);
	    //sml = new selectMouseListener(frame);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//frame.setCursor(Cursor.HAND_CURSOR);
		//frame.getView().addMouseListener(sml.getListener());
		//frame.getView().addMouseMotionListener(sml.getListener());
		frame.getView().clearListener();
        frame.setCursor(Cursor.CROSSHAIR_CURSOR);
		
		//add mouseListeners
		frame.getView().addMouseListener(insertListener.getListener());
		frame.getView().addMouseMotionListener(insertListener.getListener());
		
		System.out.println("INSERTRECTANGLE");
	}
}
