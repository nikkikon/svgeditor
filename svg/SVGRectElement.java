package svgedit.svg;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.swing.JComponent;

import org.w3c.dom.Element;

import svgedit.gui.ElementView;

/** Element describing a rectangle.
 *
 * Implements {@literal 9.8.1 Interface SVGRectElement}.
 */
public class SVGRectElement extends SVGStylableElement {

    private SVGLength x;
    private SVGLength y;
    private SVGLength width;
    private SVGLength height;
    private boolean isPaint;
    private Element elem;
    private ElementView jc;
    public SVGRectElement(SVGDocument document,Element elem) {
        super(document);
        this.elem = elem;
       
        isPaint = false;
        x = new SVGLength(document, SVGLength.SVG_DIMENSION_X);
        y = new SVGLength(document, SVGLength.SVG_DIMENSION_Y);
        width = new SVGLength(document, SVGLength.SVG_DIMENSION_X);
        height = new SVGLength(document, SVGLength.SVG_DIMENSION_Y);
    }

    /**
     * Gets the x coordinate of the rectangle. If the width is positive, this is
     * the left edge; otherwise it is one pixel past the right edge.
     * 
     * @return the x coordinate of the rectangle
     */
    public SVGLength getX() {
        return x;
    }

    /**
     * Gets the y coordinate of the rectangle. If the height is positive, this
     * is the top edge; otherwise it is one pixel past the bottom edge.
     * 
     * @return the y coordinate of the rectangle
     */
    public SVGLength getY() {
        return y;
    }

    /**
     * Gets the width of the rectangle. This may be negative.
     * 
     * @return the width
     */
    public SVGLength getWidth() {
        return width;
    }
    public void setWidth(){
    	//change length;
    }
    /**
     * Gets the height of the rectangle. This may be negative.
     * 
     * @return the height
     */
    public SVGLength getHeight() {
        return height;
    }

    public void acceptVisitor(SVGVisitor visitor) {
        visitor.visitRect(this);
    }

    public float getAbsLeft() {
        float x1 = x.getValue();
        return Math.min(x1, x1 + width.getValue());
    }

    public float getAbsRight() {
        float x1 = x.getValue();
        return Math.max(x1, x1 + width.getValue());
    }

    public float getAbsTop() {
        float y1 = y.getValue();
        return Math.min(y1, y1 + height.getValue());
    }

    public float getAbsBottom() {
        float y1 = y.getValue();
        return Math.max(y1, y1 + height.getValue());
    }

    public float getAbsWidth() {
        return Math.abs(width.getValue());
    }

    public float getAbsHeight() {
        return Math.abs(height.getValue());
    }
/*
    public Shape createShape2() {
        return new Rectangle2D.Float(getAbsLeft(), getAbsTop(), getAbsWidth(), getAbsHeight());
    }
*/
    public Shape createShape() {
        return new Rectangle2D.Float(getStrokeWidth().getValue()/2+8,getStrokeWidth().getValue()/2+8,getAbsWidth(), getAbsHeight());
    }
	@Override
	public String getShape() {
		// TODO Auto-generated method stub
		return "Rectangle";
	}

	@Override
	public float[] getDemision() {
		// TODO Auto-generated method stub
		float[] d = {x.getValue(),y.getValue(),width.getValue(),height.getValue()};
		return d;
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

	@Override
	public int getLineType() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setComponent(ElementView jc) {
		// TODO Auto-generated method stub
		this.jc = jc;
	}

	@Override
	public ElementView getComponent() {
		// TODO Auto-generated method stub
		return jc;
	}

	@Override
	public void setOffset(float dx,float dy){
		float newX;
		float newY;
		newX = getX().getValue();
		newY = getY().getValue();
		
		newX +=dx;
        newY +=dy;
      
        getX().setValue(newX);
        getY().setValue(newY);
     }

	@Override
	public void setOffsetBackToFile() {
		// TODO Auto-generated method stub
		String newXString;
		String newYString;
		 newXString = String.valueOf(getX().getValueInSpecifiedUnits())+getX().getUserUnit();
	     newYString = String.valueOf(getY().getValueInSpecifiedUnits())+getY().getUserUnit();
		jc.getElement().setAttribute("x", newXString);
		jc.getElement().setAttribute("y", newYString);
		try {
			jc.getView().getFrame().writeFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
