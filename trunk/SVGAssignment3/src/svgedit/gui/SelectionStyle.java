package svgedit.gui;

import java.util.Collection;
import svgedit.svg.SVGLength;
import svgedit.svg.SVGPaint;
import svgedit.svg.SVGStylable;
import svgedit.svg.SVGStylableElement;
import svgedit.svg.SVGElement;

/**
 * Stand-alone collection of paint attributes, representing the style of the
 * current user selection.
 * 
 * The selection style has the same paint attributes as an
 * {@link SVGStylableElement}, and represents the style of the user's current
 * selection. When multiple elements are selected, paint attributes will become
 * null if the elements do not share the same attribute value.
 * 
 */
public class SelectionStyle implements SVGStylable {

    private SVGPaint fill;
    private SVGPaint stroke;
    private SVGLength strokeWidth;

    /** Creates a new selection style with the default style. */
    public SelectionStyle() {
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

    /**
     * Sets the style to reflect the given selection.
     * 
     * If there are unstyled elements in the selection, the paint attributes are
     * set to null.
     * 
     * For each paint attribute that is equal across the entire selection (for
     * example, if the fill of all the selected elements is the same), the
     * selected attribute is updated to the selected attribute.
     * 
     * Where paint attributes differ within the selection, the selected
     * attribute is set to null.
     * 
     * @param elements
     *            the selected elements
     */
    public final void setSelectedElements(Collection<SVGElement> elements) {
        fill = null;
        stroke = null;
        strokeWidth = null;

        if (elements == null || elements.isEmpty())
            return;

        boolean first = true;
        for (SVGElement element : elements) {
            if (element instanceof SVGStylable) {
                if (first)
                    setStyle((SVGStylable) element);
                else
                    setCompatibleStyle((SVGStylable) element);
                first = false;
            }
        }
    }

    private void setStyle(SVGStylable style) {
        fill = new SVGPaint();
        fill.setValueFromPaint(style.getFill());

        stroke = new SVGPaint();
        stroke.setValueFromPaint(style.getStroke());

        strokeWidth = new SVGLength();
        strokeWidth.setValueFromLength(style.getStrokeWidth());
    }

    private void setCompatibleStyle(SVGStylable style) {
        if (!style.getFill().equals(fill))
            fill = null;
        if (!style.getStroke().equals(stroke))
            stroke = null;
        if (!style.getStrokeWidth().equals(strokeWidth))
            strokeWidth = null;
    }
}
