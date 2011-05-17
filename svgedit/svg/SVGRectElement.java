package svgedit.svg;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/** Element describing a rectangle.
 *
 * Implements {@literal 9.8.1 Interface SVGRectElement}.
 */
public class SVGRectElement extends SVGStylableElement {

    private SVGLength x;
    private SVGLength y;
    private SVGLength width;
    private SVGLength height;

    public SVGRectElement(SVGDocument document) {
        super(document);
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

    public Shape createShape() {
        return new Rectangle2D.Float(getAbsLeft(), getAbsTop(), getAbsWidth(), getAbsHeight());
    }

}
