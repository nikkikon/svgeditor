package svgedit.svg;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

import javax.swing.JComponent;

import org.w3c.dom.Element;

import svgedit.gui.ElementView;

/** Element describing a circle.
 * 
 * Specified in 9.8.2 Interface SVGCircleElement
 */
public class SVGCircleElement extends SVGStylableElement {

    private SVGLength r;
    private SVGLength cx;
    private SVGLength cy;
    private boolean isPaint;
    private Element elem;
    private ElementView jc;
    public SVGCircleElement(SVGDocument document,Element elem) {
        super(document);
        isPaint = false;
        this.elem = elem;
        r = new SVGLength(document, SVGLength.SVG_DIMENSION_OTHER);
        cx = new SVGLength(document, SVGLength.SVG_DIMENSION_X);
        cy = new SVGLength(document, SVGLength.SVG_DIMENSION_Y);
    }

    /**
     * Gets the radius length
     * 
     * @return the radius length
     */
    public SVGLength getR() {
        return r;
    }
    
    /**
     * Gets the center X coordinate
     * 
     * @return the center X coordinate
     */
    public SVGLength getCX() {
        return cx;
    }

    /**
     * Gets the center Y coordinate
     * 
     * @return the center Y coordinate
     */
    public SVGLength getCY() {
        return cy;
    }

    @Override
    public void acceptVisitor(SVGVisitor visitor) {
        visitor.visitCircle(this);
    }

    @Override
    public Shape createShape() {
        float cxValue = cx.getValue();
        float cyValue = cy.getValue();
        float rValue = Math.abs(r.getValue());
        //return new Ellipse2D.Float(cxValue - rValue, cyValue - rValue, rValue * 2, rValue * 2);
        return new Ellipse2D.Float(getStrokeWidth().getValue()/2+8, getStrokeWidth().getValue()/2+8, rValue * 2, rValue * 2);
    }

	@Override
	public String getShape() {
		// TODO Auto-generated method stub
		return "Circle";
	}
    
	@Override
	public float[] getDemision() {
		// TODO Auto-generated method stub
		float[] d ={cx.getValue()-r.getValue(),cy.getValue()-r.getValue(),r.getValue()*2,r.getValue()*2};
		return d;
	}

	@Override
	public boolean isPaint() {
		// TODO Auto-generated method stub
		return isPaint;
	}

	@Override
	public void setPaint() {
		// TODO Auto-generated method stub
		isPaint = true;
	}

	@Override
	public Element getElement() {
		// TODO Auto-generated method stub
		return elem;
	}

	@Override
	public int getLineType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setComponent(ElementView jc) {
		// TODO Auto-generated method stub
		this.jc = jc;
	}

	@Override
	public ElementView getComponent() {
		// TODO Auto-generated method stub
		return jc;
	}
	
	

	public void setOffset(float dx,float dy){
		float newX;
		float newY;
		newX = getCX().getValue();
		newY = getCY().getValue();
		newX +=dx;
        newY +=dy;
        getCX().setValue(newX);
        getCY().setValue(newY);
     }

	@Override
	public void setOffsetBackToFile() {
		// TODO Auto-generated method stub
		String newXString;
		String newYString;
		 newXString = String.valueOf(getCX().getValueInSpecifiedUnits())+getCX().getUserUnit();
	     newYString = String.valueOf(getCY().getValueInSpecifiedUnits())+getCY().getUserUnit();
		System.out.println("BACK TO FILE "+newXString );
	     jc.getElement().setAttribute("cx", newXString);
		jc.getElement().setAttribute("cy", newYString);
		try {
			jc.getView().getFrame().writeFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
