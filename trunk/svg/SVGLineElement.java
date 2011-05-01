package svgedit.svg;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.io.IOException;

import javax.swing.JComponent;

import org.w3c.dom.Element;

import svgedit.gui.ElementView;

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
    private static int UP_DOWN_LINE =1;
    private static int DOWN_UP_LINE= 2;
    private int LINE_TYPE;
    private ElementView jc;
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
    	if((x1.getValue()<=x2.getValue())&&(y1.getValue()<y2.getValue())){
    		
    		return new Line2D.Float(getStrokeWidth().getValue(), getStrokeWidth().getValue(), Math.abs(x2.getValue()-x1.getValue()), Math.abs(y2.getValue()-y1.getValue()));
    	}else if((x1.getValue()>=x2.getValue())&&(y1.getValue()>y2.getValue())){
    		
    		return new Line2D.Float(getStrokeWidth().getValue(), getStrokeWidth().getValue(), Math.abs(x2.getValue()-x1.getValue()), Math.abs(y2.getValue()-y1.getValue()));
    	}else if((x1.getValue()<x2.getValue())&&(y1.getValue()>=y2.getValue())){
    		
    		return new Line2D.Float(getStrokeWidth().getValue(),y1.getValue()-y2.getValue(),  Math.abs(x2.getValue()-x1.getValue()), getStrokeWidth().getValue());
    	}else if((x1.getValue()>x2.getValue())&&(y1.getValue()<=y2.getValue())){
    		
    		return new Line2D.Float(x1.getValue()-x2.getValue(),getStrokeWidth().getValue(), getStrokeWidth().getValue(), Math.abs(y2.getValue()-y1.getValue()));
    	}
    	return null;
    }

	@Override
	public String getShape() {
		// TODO Auto-generated method stub
		return "Line";
	}
	public void setLineType(int type){
		LINE_TYPE = type;
	}
	public int getLineType(){
		return LINE_TYPE;
	}

	@Override
	public float[] getDemision() {
		// TODO Auto-generated method stub
;
		if((x1.getValue()<=x2.getValue())&&(y1.getValue()<y2.getValue())){
			float[] d = {x1.getValue(),y1.getValue(),Math.abs(x2.getValue()-x1.getValue()),Math.abs(y2.getValue()-y1.getValue())};
			setLineType(UP_DOWN_LINE);
			return d;
		}
		else if((x1.getValue()>=x2.getValue())&&(y1.getValue()>y2.getValue())){
			float[] d = {x2.getValue(),y2.getValue(),Math.abs(x2.getValue()-x1.getValue()),Math.abs(y2.getValue()-y1.getValue())};
			setLineType(UP_DOWN_LINE);
			return d;
		}
		else if((x1.getValue()<x2.getValue())&&(y1.getValue()>=y2.getValue())){
			float[] d = {x1.getValue(),y2.getValue(),Math.abs(x2.getValue()-x1.getValue()),Math.abs(y2.getValue()-y1.getValue())};
			setLineType(DOWN_UP_LINE);
			return d;
		}
		else if((x1.getValue()>x2.getValue())&&(y1.getValue()<=y2.getValue())){
			float[] d = {x2.getValue(),y1.getValue(),Math.abs(x2.getValue()-x1.getValue()),Math.abs(y2.getValue()-y1.getValue())};
			setLineType(DOWN_UP_LINE);
			return d;
		}
		return null;
		
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
		float newX1;
		float newY1;
		float newX2;
		float newY2;
		newX1 = getX1().getValue();
		newY1 = getY1().getValue();
		newX2 = getX2().getValue();
		newY2 = getY2().getValue();
		System.out.println("OLDX1"+newX1); 
        System.out.println("OLDY1"+newY1); 
		newX1 +=dx;
        newY1 +=dy;
        newX2 +=dx;
        newY2 +=dy;
        getX1().setValue(newX1);
        getY1().setValue(newY1);
        getX2().setValue(newX2);
        getY2().setValue(newY2);
        System.out.println("OFFSET DX"+dx);
        System.out.println("OFFSET DY"+dy);
        
        System.out.println("NEWX1"+newX1); 
        System.out.println("NEWY1"+newY1);
       
        
	}

	@Override
	public void setOffsetBackToFile() {
		// TODO Auto-generated method stub
		String newX1String;
		String newY1String;
		String newX2String;
		String newY2String;
		 newX1String = String.valueOf(getX1().getValueInSpecifiedUnits())+getX1().getUserUnit();
	     newY1String = String.valueOf(getY1().getValueInSpecifiedUnits())+getY1().getUserUnit();
		
	     newX2String = String.valueOf(getX2().getValueInSpecifiedUnits())+getX1().getUserUnit();
	     newY2String = String.valueOf(getY2().getValueInSpecifiedUnits())+getY1().getUserUnit();
	    jc.getElement().setAttribute("x1", newX1String);
		jc.getElement().setAttribute("y1", newY1String);
		jc.getElement().setAttribute("x2", newX2String);
		jc.getElement().setAttribute("y2", newY2String);
		try {
			jc.getView().getFrame().writeFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
