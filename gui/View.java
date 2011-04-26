package svgedit.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import javax.swing.JComponent;

import svgedit.svg.SVGDocument;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGLength;
import svgedit.svg.SVGPaint;
import svgedit.svg.SVGStylable;
import svgedit.svg.SVGViewport;

/** A view onto an {@link SVGDocument}. */
@SuppressWarnings("serial")
public class View extends JComponent implements SVGViewport {

    private SVGDocument document;
    private ElementView elementView;
    private Frame frame;
    //private int paintTimes;
    /** Creates a new view */
   // public View() {
    	
    //}
    public View(Frame frame) {
    	this.frame = frame;
    }

    public float getViewportWidth() {
        return getWidth();
    }

    public float getViewportHeight() {
        return getHeight();
    }
    public Frame getFrame(){
    	return frame;
    }
    /** Internal paint entry point.  All drawing in the view uses a {@link Graphics2D},
     *  so for convenience this method simply delegates to {@link #paint2D}.
     *
     *  @param g the {@link Graphics} context to paint to
     */
    @Override
    protected void paintComponent(Graphics g) {
    	
        paint2D((Graphics2D) g);
    }

    /** Paints the entire view.
     *
     * @param g the {@link Graphics2D} context to paint to
     */
    private void paint2D(Graphics2D g) {
        // Paint view background
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Paint document background and border
        int documentWidth = (int) document.getWidth().getValue();
        int documentHeight = (int) document.getHeight().getValue();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, documentWidth, documentHeight);
        g.setColor(Color.BLACK);
        g.drawRect(-1, -1, documentWidth, documentHeight);
       // 
      
        // Paint document
        for (SVGElement elem : document) {
        	
        	if(!elem.isPaint()){
        		elementView = new ElementView(elem,this);
            	//elementView.paintElement(g, elem);
            	add(elementView);
            	frame.addJComponent(elementView);
            	
            	elem.setPaint();
        	}
        	
    	
        }
    }

    /** Paints a single element on the graphics context.
     *
     * @param g the graphics context to paint to
     * @param elem the element to paint
     */
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
        
    }
    **/

    /** Gets the document currently being displayed by the view. */
    public SVGDocument getDocument() {
        return document;
    }

    /** Sets the document that the view should display.
     *
     * @param document the document to set
     */
    public void setDocument(SVGDocument document) {
        this.document = document;
        repaint();
    }

}
