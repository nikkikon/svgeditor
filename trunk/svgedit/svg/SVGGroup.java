package svgedit.svg;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Iterator;

/** Represents a {@literal 'g'} element in the document.
 * 
 * We do not currently support style inheritance, so there is no need to
 * implement SVGStylable here (though in a full SVG implementation we would).
 * 
 */
public class SVGGroup extends SVGElement implements Iterable<SVGElement> {

    private ArrayList<SVGElement> children;

    public SVGGroup(SVGDocument document) {
        super(document);
        children = new ArrayList<SVGElement>();
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

}
