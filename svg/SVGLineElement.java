package svgedit.svg;

import java.awt.Shape;
import java.awt.geom.Line2D;

import org.w3c.dom.Element;

/** Element describing a line segment.
 *
 * Implements {@literal 9.8.4 Interface SVGLineElement}.
 */
public class SVGLineElement extends SVGStylableElement {

    private SVGLength x1;
    private SVGLength y1;
    private SVGLength x2;
    private SVGLength y2;
    private boolean isPaint;
    private Element elem;
    public SVGLineElement(SVGDocument document,Element elem) {
        super(document);
        isPaint = false;
        this.elem = elem;
        x1 = new SVGLength(document, SVGLength.SVG_DIMENSION_X);
        y1 = new SVGLength(document, SVGLength.SVG_DIMENSION_Y);
        x2 = new SVGLength(document, SVGLength.SVG_DIMENSION_X);
        y2 = new SVGLength(document, SVGLength.SVG_DIMENSION_Y);
    }

    /**
     * Gets the first X coordinate
     * 
     * @return the first X coordinate
     */
    public SVGLength getX1() {
        return x1;
    }
    
    /**
     * Gets the first Y coordinate
     * 
     * @return the first Y coordinate
     */
    public SVGLength getY1() {
        return y1;
    }

    /**
     * Gets the second X coordinate
     * 
     * @return the second X coordinate
     */
    public SVGLength getX2() {
        return x2;
    }

    /**
     * Gets the second Y coordinate
     * 
     * @return the second Y coordinate
     */
    public SVGLength getY2() {
        return y2;
    }

    @Override
    public void acceptVisitor(SVGVisitor visitor) {
        visitor.visitLine(this);
    }

    @Override
    public Shape createShape() {
       // return new Line2D.Float(x1.getValue(), y1.getValue(), x2.getValue(), y2.getValue());
    	return new Line2D.Float(getStrokeWidth().getValue(), getStrokeWidth().getValue(), Math.abs(x2.getValue()-x1.getValue()), Math.abs(y2.getValue()-y1.getValue()));
    }

	@Override
	public String getShape() {
		// TODO Auto-generated method stub
		return "Line";
	}

	@Override
	public float[] getDemision() {
		// TODO Auto-generated method stub
;
		if(x1.getValue()<x2.getValue()){
			float[] d = {x1.getValue(),y1.getValue(),Math.abs(x2.getValue()-x1.getValue()),Math.abs(y2.getValue()-y1.getValue())};
		    return d;
		}
		else{
			float[] d = {x2.getValue(),y2.getValue(),Math.abs(x2.getValue()-x1.getValue()),Math.abs(y2.getValue()-y1.getValue())};
		    return d;
		}
		
	}
	
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

}
