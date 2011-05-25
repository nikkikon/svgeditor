package svgedit.gui.controlpoints;

import svgedit.gui.manipulators.ControlPointManipulator;

/** An abstract description of a point that can be manipulated by the user
 *  to modify an element.
 *
 *  @see ControlPointManipulator
 */
public abstract class ControlPoint {

    /** Gets the X position of this control point
     *
     * @return the X position of the control point
     */
    public abstract float getX();

    /** Gets the Y position of this control point
     *
     * @return the Y position of the control point
     */
    public abstract float getY();

    /** Sets the position of this control point.  This should be implemented
     * so that the modification to the control point directly affects the
     * represented element.
     *
     * For example, calling {@link #set} on a
     * {@link svgedit.gui.controlpoints.RectControlPoint} modifies
     * the actual position and size of the {@link svgedit.svg.SVGRectElement}.
     *
     * @param x the X coordinate to set
     * @param y the Y coordinate to set
     */
    public abstract void set(float x, float y);

}
