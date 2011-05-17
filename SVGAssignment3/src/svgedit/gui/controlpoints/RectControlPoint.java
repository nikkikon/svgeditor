package svgedit.gui.controlpoints;

import svgedit.svg.SVGRectElement;

/** Displays a control point at the edge or vertex of a rectangle.
 *
 * The provided flags determine where on the rectangle the control point is
 * displayed, and hence which attributes of the rectangle it modifies.
 */
public class RectControlPoint extends ControlPoint {

    private SVGRectElement rect;

    private int flags;

    public static final int LEFT = 1;
    public static final int TOP = 2;
    public static final int RIGHT = 4;
    public static final int BOTTOM = 8;

    /**
     * Constructs a {@link RectControlPoint} at a point on the given rectangle.
     * 
     * The point is given by a combination of bit flags as the second argument.
     * For example, <code>(LEFT | TOP)</code> defines the control point at the
     * top-left corner of the rectangle. Only one of <code>LEFT</code> and
     * <code>RIGHT</code> may be specified (or (neither); similarly only one or
     * both of <code>TOP</code> and <code>BOTTOM</code> can be present (or
     * neither). When both flags for an axis are omitted, the control point appears
     * on the center of the edge.
     * 
     * The control point will be drawn at the specified position, and allows the
     * user to drag that point to manipulate the rectangle.
     * 
     * Note that the flag names <code>LEFT</code>, <code>RIGHT</code>, etc. are
     * figurative, and will be reversed in their actual position when the
     * dimensions of the rectangle are negative.
     * 
     * @param rect
     *            rectangle to create control point on
     * @param flags
     *            bitwise combination of flags giving position on rectangle
     */
    public RectControlPoint(SVGRectElement rect, int flags) {
        this.rect = rect;
        this.flags = flags;

        // Ensure flags are a valid combination
        if (flags == 0 ||                                       // at least one flag
            ((flags & LEFT) != 0 && (flags & RIGHT) != 0) ||    // cannot specify left and right
            ((flags & TOP) != 0 && (flags & BOTTOM) != 0) ||    // cannot specify bottom and top
            flags > 0xf)                                        // all flags recognized
            throw new IllegalArgumentException("Invalid flags");

    }

    @Override
    public float getX() {
        float x1 = rect.getX().getValue();
        float x2 = x1 + rect.getWidth().getValue();

        if ((flags & LEFT) != 0)
            return x1;
        else if ((flags & RIGHT) != 0)
            return x2;
        else
            return (x1 + x2) / 2.0f; // center horizontally
    }

    @Override
    public float getY() {
        float y1 = rect.getY().getValue();
        float y2 = y1 + rect.getHeight().getValue();

        if ((flags & TOP) != 0)
            return y1;
        else if ((flags & BOTTOM) != 0)
            return y2;
        else
            return (y1 + y2) / 2.0f; // center vertically
    }

    @Override
    public void set(float x, float y) {
        if ((flags & LEFT) != 0) {
            float width = rect.getWidth().getValue();
            rect.getWidth().setValue(width - x + rect.getX().getValue());
            rect.getX().setValue(x);
        } else if ((flags & RIGHT) != 0)
            rect.getWidth().setValue(x - rect.getX().getValue());

        if ((flags & TOP) != 0) {
            float height = rect.getHeight().getValue();
            rect.getHeight().setValue(height - y + rect.getY().getValue());
            rect.getY().setValue(y);
        } else if ((flags & BOTTOM) != 0)
            rect.getHeight().setValue(y - rect.getY().getValue());
    }

}
