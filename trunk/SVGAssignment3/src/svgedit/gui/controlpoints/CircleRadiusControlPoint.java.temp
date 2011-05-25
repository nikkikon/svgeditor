package svgedit.gui.controlpoints;

import svgedit.svg.SVGCircleElement;

/** Displays a control point on the edge of a circle element that the user
 *  can move to change the radius of the circle.
 */
public class CircleRadiusControlPoint extends ControlPoint {

    private SVGCircleElement circle;

    /** Create the control point for the given circle element
     *
     * @param circle the element to modify
     */
    public CircleRadiusControlPoint(SVGCircleElement circle) {
        this.circle = circle;
    }

    @Override
    public float getX() {
        return circle.getCX().getValue() + circle.getR().getValue();
    }

    @Override
    public float getY() {
        return circle.getCY().getValue();
    }

    @Override
    public void set(float x, float y) {
        float r = x - circle.getCX().getValue();
        circle.getR().setValue(r);
    }

}
