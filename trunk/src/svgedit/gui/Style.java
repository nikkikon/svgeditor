package svgedit.gui;

import java.awt.Color;
import svgedit.svg.SVGLength;
import svgedit.svg.SVGPaint;
import svgedit.svg.SVGStylable;

/**
 * Stand-alone collection of style attributes.
 * 
 */
public class Style implements SVGStylable {

    private SVGPaint fill;
    private SVGPaint stroke;
    private SVGLength strokeWidth;

    /** Creates a new style collection with default values */
    public Style() {
        fill = new SVGPaint();
        fill.setRGBColor(Color.BLACK);

        stroke = new SVGPaint();
        stroke.setRGBColor(null);

        strokeWidth = new SVGLength();
        strokeWidth.newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, 1.0f);
    }

    /** Gets the fill paint
     *
     * @return a paint value indicating the style's current fill.
     */
    public SVGPaint getFill() {
        return fill;
    }

    /** Gets the stroke paint
     *
     * @return a paint value indicating the style's current stroke.
     */
    public SVGPaint getStroke() {
        return stroke;
    }

    /** Gets the stroke width
     *
     * @return a length value indicating the style's current stroke length.
     */
    public SVGLength getStrokeWidth() {
        return strokeWidth;
    }

}
