package svgedit.gui.actions;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;
import svgedit.insert.InsertCircleListener;

public class InsertCircleAction extends AbstractAction{

	private Frame frame;
	private InsertCircleListener insertListener;	
	public InsertCircleAction(Frame frame){
		super("Insert Circle");
	    this.frame = frame;
	    insertListener = new InsertCircleListener(frame);
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 frame.getView().clearListener();
		frame.setCursor(Cursor.CROSSHAIR_CURSOR);
		
		//add insertListener
       
        
		frame.getView().addMouseListener(insertListener.getListener());
		frame.getView().addMouseMotionListener(insertListener.getListener());
		
		System.out.println("INSERT CIRCLE");
	}
	
}
