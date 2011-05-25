package svgedit.svg;

/**
 * Represents an element that has presentation attributes.
 * 
 * Corresponds approximately to 4.5.22 Interface SVGStylable, however because we
 * don't support CSS styling we expose the presentation attributes directly.
 */
public interface SVGStylable {

    /** Gets the paint attribute used to fill the element.
     *
     * @return an SVG paint object describing the fill for the element
     */
    public SVGPaint getFill();

    /** Gets the paint attribute used to stroke the element.
     *
     * @return an SVG paint object describing the stroke for the element
     */
    public SVGPaint getStroke();

    /** Gets the stroke width of the element
     *
     * @return an SVG length describing the stroke width of the element
     */
    public SVGLength getStrokeWidth();

}
