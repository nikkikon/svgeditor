package svgedit.svg;

import java.awt.Color;

/** Base class for all stylable elements.
 *
 * This class implements the {@link SVGStylable} interface by storing the style
 * attributes directly on the object.
 */
public abstract class SVGStylableElement extends SVGElement implements SVGStylable {

    private SVGPaint fill;
    private SVGPaint stroke;
    private SVGLength strokeWidth;

    public SVGStylableElement(SVGDocument document) {
        super(document);

        // 11.3 initial fill paint is black
        this.fill = new SVGPaint();
        this.fill.setRGBColor(Color.BLACK);

        // 11.4 initial stroke paint is 'none'
        this.stroke = new SVGPaint();
        this.stroke.setRGBColor(null);

        // 11.4 initial stroke width is 1
        this.strokeWidth = new SVGLength(document, SVGLength.SVG_DIMENSION_OTHER);
        this.strokeWidth.newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, 1.0f);
    }

    public SVGPaint getFill() {
        return fill;
    }

    public SVGPaint getStroke() {
        return stroke;
    }

    public SVGLength getStrokeWidth() {
        return strokeWidth;
    }

    /** Copy the style attributes from another stylable object.
     *
     * @param stylable the object containing attributes to copy
     */
    public void setStyleFromStylable(SVGStylable stylable) {
        fill.setValueFromPaint(stylable.getFill());
        stroke.setValueFromPaint(stylable.getStroke());
        strokeWidth.setValueFromLength(stylable.getStrokeWidth());
    }
}
