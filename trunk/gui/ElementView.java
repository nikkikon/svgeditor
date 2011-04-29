package svgedit.gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.w3c.dom.Element;


import svgedit.resize.ResizeMouseListener;
import svgedit.resize.ResizeMouseListenerCircle;
import svgedit.resize.ResizeMouseListenerLine;
import svgedit.resize.ResizeMouseListenerRect;
import svgedit.resize.ResizeableBorder;
import svgedit.resize.ResizeableBorderRectangle;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;
import svgedit.svg.SVGLength;
import svgedit.svg.SVGPaint;
import svgedit.svg.SVGStylable;

public class ElementView extends JComponent{
	private ResizeMouseListener rml;
	private int x,y,h,w,strokewidth;
	private SVGElement elem;
	private View view;
	public ElementView(SVGElement elem,View view){
		//paintElement(g,elem);
		this.view = view;
		this.elem = elem;
		
		//elem.setComponent(); bind the component to svgelement
	
		
	    //System.out.println(elem);
	   
	    	x = (int) elem.getDemision()[0];
	    	y = (int) elem.getDemision()[1];
	    	w = (int) elem.getDemision()[2];
	    	h = (int) elem.getDemision()[3];
	    	strokewidth = (int) elem.getStrokeWidth().getValue();
	 
	    	if(elem.getShape().equals("Line")){
	    		setBounds(x-strokewidth/4,y-strokewidth/4,w+strokewidth,h+strokewidth);
	    	}
	    	else{
	    		setBounds(x-8-strokewidth/2, y-8-strokewidth/2, w+strokewidth+16, h+strokewidth+16);	
	    	}
	    	rml = new ResizeMouseListener(this,elem.getShape(),elem.getLineType());
	        MouseInputListener resizeListener = rml.getListener();
	        ResizeableBorder  rb = rml.getBorder();
	        addMouseListener(resizeListener);
		    addMouseMotionListener(resizeListener);
		    setBorder(rb);
		    
	}
   public void paint(Graphics g){
		super.paint(g);
		//System.out.println("PAINT");

    	
		if (!(elem instanceof SVGStylable))
            return;

        Shape shape = elem.createShape();
        SVGStylable style = (SVGStylable) elem;
        SVGPaint fillPaint = style.getFill();
        SVGPaint strokePaint = style.getStroke();
        SVGLength strokeWidth = style.getStrokeWidth();

        // Fill the interior of the shape
        if (fillPaint.getPaintType() == SVGPaint.SVG_PAINTTYPE_RGBCOLOR) {
            ((Graphics2D) g).setPaint(fillPaint.getRGBColor());
            ((Graphics2D) g).fill(shape);
        }

        // Stroke the outline of the shape
        if (strokePaint.getPaintType() == SVGPaint.SVG_PAINTTYPE_RGBCOLOR) {
            Stroke stroke = new BasicStroke(strokeWidth.getValue());
            ((Graphics2D) g).setStroke(stroke);
            g.setColor(strokePaint.getRGBColor());
            ((Graphics2D) g).draw(shape);
        }
        
       
	}
   
   public SVGElement getSVGElement(){
	   return elem;
   }
	
   public Element getElement(){
	   return elem.getElement();
   }
   
   public View getView(){
	   return view;
   }
   
   public SVGGroup getGroup(){
	   return elem.getParent();
   }
   
   public ResizeMouseListener getResizeMouseListener(){

		   return rml;

   }
	/*public void paintElement(Graphics2D g, SVGElement elem) {

		
    	if (!(elem instanceof SVGStylable))
            return;

        Shape shape = elem.createShape();

        SVGStylable style = (SVGStylable) elem;
        SVGPaint fillPaint = style.getFill();
        SVGPaint strokePaint = style.getStroke();
        SVGLength strokeWidth = style.getStrokeWidth();

        // Fill the interior of the shape
        if (fillPaint.getPaintType() == SVGPaint.SVG_PAINTTYPE_RGBCOLOR) {
            g.setPaint(fillPaint.getRGBColor());
            g.fill(shape);
        }

        // Stroke the outline of the shape
        if (strokePaint.getPaintType() == SVGPaint.SVG_PAINTTYPE_RGBCOLOR) {
            Stroke stroke = new BasicStroke(strokeWidth.getValue());
            g.setStroke(stroke);
            g.setColor(strokePaint.getRGBColor());
            g.draw(shape);
        }
        //ResizeMouseListener rl = new ResizeMouseListener(this,elem.getShape());
        //MouseInputListener resizeListener = rl.getListener();
       // paint(g);
        
        //rl.getBorder().print();
	    
        
    }
    **/
 
}
