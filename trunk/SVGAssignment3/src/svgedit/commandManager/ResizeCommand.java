package svgedit.commandManager;

import java.awt.Point;
import svgedit.gui.View;
import svgedit.gui.controlpoints.ControlPoint;

public class ResizeCommand implements CommandInterface{	
	private View view;
	//private ArrayList<SVGElement>elements = new ArrayList<SVGElement>();
    private Point iniPoint;
    private Point lastPoint;
    private ControlPoint controlPoint;   
	public ResizeCommand(View view,Point iniPoint,Point lastPoint,ControlPoint controlPoint){
        this.view=view;
        this.iniPoint=iniPoint;
        this.lastPoint=lastPoint;
        this.controlPoint = controlPoint;
	}
	public void execute(){
		
		float dx = lastPoint.x- iniPoint.x;
		float dy = lastPoint.y - iniPoint.y;
        controlPoint.set(controlPoint.getX()+dx, controlPoint.getY()+dy);
        view.getDocument().setModified(true);
        view.repaint();
        view.getFrame().setUndoEnable();
     }
	public void undo(){		
		float dx = iniPoint.x-lastPoint.x;
		float dy = iniPoint.y-lastPoint.y;
        controlPoint.set(controlPoint.getX()+dx, controlPoint.getY()+dy);
        view.getDocument().setModified(true);
        view.repaint();
	}
}
	
	
