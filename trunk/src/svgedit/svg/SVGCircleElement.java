package svgedit.svg;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/** Element describing a circle.
 * 
 * Specified in 9.8.2 Interface SVGCircleElement
 */
public class SVGCircleElement extends SVGStylableElement {

    private SVGLength r;
    private SVGLength cx;
    private SVGLength cy;

    public SVGCircleElement(SVGDocument document) {
        super(document);

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
        return new Ellipse2D.Float(cxValue - rValue, cyValue - rValue, rValue * 2, rValue * 2);
    }

}
