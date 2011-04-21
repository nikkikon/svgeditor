package svgedit.svg;

import java.awt.Shape;
import java.awt.geom.Line2D;

/** Element describing a line segment.
 *
 * Implements {@literal 9.8.4 Interface SVGLineElement}.
 */
public class SVGLineElement extends SVGStylableElement {

    private SVGLength x1;
    private SVGLength y1;
    private SVGLength x2;
    private SVGLength y2;

    public SVGLineElement(SVGDocument document) {
        super(document);

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
        return new Line2D.Float(x1.getValue(), y1.getValue(), x2.getValue(), y2.getValue());
    }

}
