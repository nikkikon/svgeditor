package svgedit.svg;

import java.awt.Shape;

import org.w3c.dom.Element;

/** An SVG visual element.
 *
 * The interfaces defined here roughly correspond to the SVG DOM interfaces
 * defined in {@literal 4.5.1 Interface SVGElement}, but are modified to suit
 * the restricted implementation required here.
 *
 * All SVG elements have a link to their parent and the document they belong
 * to.  These links are maintained automatically as elements are inserted into
 * and removed from groups.
 */
public abstract class SVGElement {

    private SVGDocument document;
    private SVGGroup parent;

    /** Creates an element within a document.
     *
     * @param document the document that this element belongs to
     */
    public SVGElement(SVGDocument document) {
        this.document = document;
    }

    /**
     * Gets the document this element belongs to.
     * 
     * @return an SVG document
     */
    public final SVGDocument getDocument() {
        return document;
    }

    /**
     * Gets the group element that contains this element.
     * 
     * @return the parent group, or null if the group is the document root group
     */
    public final SVGGroup getParent() {
        return parent;
    }

    /**
     * Sets the parent of this element. Called only from Group as a result of
     * child list modifications.
     * 
     * @param parent
     *            the parent that contains this group
     */
    final void setParent(SVGGroup parent) {
        this.parent = parent;
    }

    /**
     * Creates an AWT shape representing this element's visual appearance.
     * 
     * @return a new shape representing the element
     */
    public abstract Shape createShape();

    /** Calls the appropriate visit method on the given visitor. */
    public abstract void acceptVisitor(SVGVisitor visitor);
    
    public abstract String getShape();
    
    public abstract float[] getDemision();
    public abstract SVGLength getStrokeWidth();
    public abstract boolean isPaint();
    public abstract void setPaint();
    public abstract Element getElement();
}
