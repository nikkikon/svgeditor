package svgedit.svg;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;

import org.w3c.dom.Element;

import svgedit.gui.ElementView;

/** Represents a {@literal 'g'} element in the document.
 * 
 * We do not currently support style inheritance, so there is no need to
 * implement SVGStylable here (though in a full SVG implementation we would).
 * 
 */
public class SVGGroup extends SVGElement implements Iterable<SVGElement> {

    private ArrayList<SVGElement> children;
    private boolean isPaint;
    private Element elem;
    private ElementView jc;
    public SVGGroup(SVGDocument document,Element elem) {
        super(document);
        this.elem = elem;
        children = new ArrayList<SVGElement>();
        isPaint=false;
    }
    public SVGGroup(SVGDocument document) {
        super(document);
        children = new ArrayList<SVGElement>();
        isPaint=false;
    }

    /**
     * Gets a copy of the list of child elements contained in the group.
     * 
     * @return an array of child elements
     */
    public SVGElement[] getChildren() {
        return children.toArray(new SVGElement[0]);
    }

    public Iterator<SVGElement> iterator() {
        return children.iterator();
    }

    /** Adds a child element to the group.
     *
     * @param child the element to add
     */
    public void appendChild(SVGElement child) {
        assert child.getParent() == null;
        children.add(child);
        child.setParent(this);
    }

    /** Removes a child element from the group.
     *
     * @param child the child to remove
     */
    public void removeChild(SVGElement child) {
        assert child.getParent() == this;
        children.remove(child);
        child.setParent(null);
    }

    public Shape createShape() {
        return null;
    }

    public void acceptVisitor(SVGVisitor visitor) {
        visitor.visitGroup(this);
    }

	@Override
	public String getShape() {
		// TODO Auto-generated method stub
		return "Group";
	}

	@Override
	public float[] getDemision() {
		float[] d = {0,0,0,0};
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGLength getStrokeWidth() {
		// TODO Auto-generated method stub
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
	@Override
	public int getLineType() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setComponent(ElementView jc) {
		// TODO Auto-generated method stub
		this.jc = null;
	}

	@Override
	public ElementView getComponent() {
		// TODO Auto-generated method stub
		return jc;
	}
	@Override
	public void setOffset(float dx, float dy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setOffsetBackToFile() {
		// TODO Auto-generated method stub
		
	}

}
