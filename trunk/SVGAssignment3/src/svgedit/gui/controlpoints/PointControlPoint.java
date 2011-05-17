package svgedit.gui.controlpoints;

import svgedit.svg.SVGLength;

/**
 * Displays a control point that directly modifies a given {@literal x} and
 * {@literal y} length attribute.
 * 
 */
public class PointControlPoint extends ControlPoint {

    SVGLength x;
    SVGLength y;

    /** Create the control point to modify the given values
     *
     * @param x the X value to manipulate
     * @param y the Y value to manipulate
     */
    public PointControlPoint(SVGLength x, SVGLength y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() {
        return x.getValue();
    }

    @Override
    public float getY() {
        return y.getValue();
    }

    @Override
    public void set(float x, float y) {
        this.x.setValue(x);
        this.y.setValue(y);
    }

}
