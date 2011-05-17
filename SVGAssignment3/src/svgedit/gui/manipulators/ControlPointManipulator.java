package svgedit.gui.manipulators;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import svgedit.gui.View;
import svgedit.gui.controlpoints.ControlPoint;

/** Manipulator for an arbitrary control point.  Draws the control point
 *  as a white box with black outline.  The user can drag the control point
 *  within the view to modify its position.
 *
 */
public class ControlPointManipulator extends Manipulator {

    private ControlPoint controlPoint;
    private static final int SIZE = 4;
    private Point offset;

    /** Creates a manipulator for the given view to modify a control point.
     *
     * @param view the view this manipulator belongs to
     * @param controlPoint the control point to manipulate
     */
    public ControlPointManipulator(View view, ControlPoint controlPoint) {
        super(view);
        this.controlPoint = controlPoint;
    }

    @Override
    public void paint(Graphics2D g2d) {
        int x = (int) controlPoint.getX();
        int y = (int) controlPoint.getY();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x - SIZE, y - SIZE, SIZE * 2, SIZE * 2);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x - SIZE, y - SIZE, SIZE * 2, SIZE * 2);
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        int x = (int) controlPoint.getX();
        int y = (int) controlPoint.getY();

        if (Math.abs(e.getX() - x) < SIZE * 2 && Math.abs(e.getY() - y) < SIZE * 2) {
            offset = new Point(e.getX() - x, e.getY() - y);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        return true;
    }

    @Override
    public boolean mouseDragged(MouseEvent e) {
        int x = e.getX() - offset.x;
        int y = e.getY() - offset.y;
        controlPoint.set(x, y);
        getView().getDocument().setModified(true);
        return true;
    }

}
