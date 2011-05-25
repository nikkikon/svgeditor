package svgedit.commandManager;

import java.awt.Color;
import java.awt.Event;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import svgedit.gui.Frame;
import svgedit.gui.View;
import svgedit.svg.SVGCircleElement;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;
import svgedit.svg.SVGLineElement;
import svgedit.svg.SVGPaint;
import svgedit.svg.SVGRectElement;
import svgedit.svg.SVGStylable;
import svgedit.svg.SVGVisitor;

public class MoveCommand implements CommandInterface{

	private Frame frame;
	private View view;
	//private ArrayList<SVGElement>elements = new ArrayList<SVGElement>();
    private Point initpoint;
    private Point lastpoint;
    private SVGVisitor visitor;
    private SVGElement [] elements;
    
	public MoveCommand(View view,Point initpoint,Point lastpoint,SVGElement [] elements){
        this.view=view;
        this.initpoint=initpoint;
        this.lastpoint=lastpoint;
        this.elements=elements;
	}
	public void execute(){
		
		
		float dx = (float) (lastpoint.getX() - initpoint.getX());
        float dy = (float) (lastpoint.getY() - initpoint.getY());

        visitor = new MoveElementVisitor(dx, dy);

        for (SVGElement element : elements) {
        	
        	element.acceptVisitor(visitor);
        }

        view.getDocument().setModified(true);
        view.repaint();
     }
	public void undo(){		
		float dx = (float) (initpoint.getX()-lastpoint.getX());
        float dy = (float) (initpoint.getY()-lastpoint.getY());

        visitor = new MoveElementVisitor(dx, dy);

        for (SVGElement element : elements) {
        	
        	element.acceptVisitor(visitor);
        }

        view.getDocument().setModified(true);
        view.repaint();
/*
		for(int i = 0; i<elements.size();i++){
			System.out.println(elements.get(i));
			SVGPaint svgPaint = new SVGPaint();
			svgPaint.setRGBColor(paints.get(i));
			System.out.println(paints.get(i));
			((SVGStylable)elements.get(i)).getFill().setValueFromPaint(svgPaint);
		}
		view.repaint();
		*/
	}
	
	
	private static class MoveElementVisitor implements SVGVisitor {

        private float dx, dy;

        public MoveElementVisitor(float dx, float dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public void visitGroup(SVGGroup group) {
            for (SVGElement child : group)
                child.acceptVisitor(this);
        }

        public void visitRect(SVGRectElement rect) {
            rect.getX().setValue(rect.getX().getValue() + dx);
            rect.getY().setValue(rect.getY().getValue() + dy);
        }

        public void visitCircle(SVGCircleElement circle) {
            circle.getCX().setValue(circle.getCX().getValue() + dx);
            circle.getCY().setValue(circle.getCY().getValue() + dy);
        }

        public void visitLine(SVGLineElement line) {
            line.getX1().setValue(line.getX1().getValue() + dx);
            line.getY1().setValue(line.getY1().getValue() + dy);
            line.getX2().setValue(line.getX2().getValue() + dx);
            line.getY2().setValue(line.getY2().getValue() + dy);
        }

    }
}