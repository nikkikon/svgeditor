package svgedit.svg;

/** Interface for visiting SVG elements.  Implement this interface to provide
 *  specific support for elements based on their types.  Call
 * {@link svgedit.svg.SVGElement#acceptVisitor} to have the appopriate {@literal visit}
 * method invoked on the interface.
 */
public interface SVGVisitor {

    /** Visits a group element.
     *
     * @param group the SVG group element
     */
    public void visitGroup(SVGGroup group);

    /** Visits a rect element.
     *
     * @param rect the SVG rect element
     */
    public void visitRect(SVGRectElement rect);

    /** Visits a circle element.
     *
     * @param circle the SVG circle element
     */
    public void visitCircle(SVGCircleElement circle);

    /** Visits a line element.
     *
     * @param line the SVG line element
     */
    public void visitLine(SVGLineElement line);

}
