package svgedit.commandManager;

import java.awt.Point;
import svgedit.gui.Frame;
import svgedit.gui.View;
import svgedit.gui.controlpoints.ControlPoint;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGVisitor;

public class ResizeCommand implements CommandInterface{

	private Frame frame;
	private View view;
	//private ArrayList<SVGElement>elements = new ArrayList<SVGElement>();
    private Point iniPoint;
    private Point lastPoint;
    private SVGVisitor visitor;
    private ControlPoint controlPoint;
    private Point newOffset;
    private SVGElement [] elements;
    
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
     }
	public void undo(){		
		float dx = iniPoint.x-lastPoint.x;
		float dy = iniPoint.y-lastPoint.y;
        controlPoint.set(controlPoint.getX()+dx, controlPoint.getY()+dy);
        view.getDocument().setModified(true);
        view.repaint();
	}
}
	
