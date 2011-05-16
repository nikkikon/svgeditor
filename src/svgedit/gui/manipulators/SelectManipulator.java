package svgedit.gui.manipulators;

import java.awt.*;
import java.awt.event.*;
import svgedit.gui.View;

import svgedit.svg.*;

/** Manipulator for selecting elements within the view.  The user can either
 * click on an element to select it, or draw a rectangle around elements to
 * select multiple elements.
 */
public class SelectManipulator extends Manipulator {

    // Points defining selection rectangle
    private Point dragStart;
    private Point dragCurrent;

    Stroke dashedStroke;

    /** Constructs a manipulator for selecting elements.
     *
     * @param view the view this manipulator belongs to
     */
    public SelectManipulator(View view) {
        super(view);

        dashedStroke = new BasicStroke(1.0f, // width
        BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, // miter limit
        new float[] { 3.0f, 2.0f, }, // dash array
        0.0f); // dash phase

        dragStart = new Point();
        dragCurrent = new Point();
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        View view = getView();
        Point point = e.getPoint();

        SVGElement elem = view.getElementAtPoint(point);
        if (elem != null) {
            // Directly selected an element; this is handled directly by view
            return false;
        } else {
            // Begin drawing a selection rectangle
            dragStart = point;
            dragCurrent = point;
        }

        return true;
    }

    @Override
    public boolean mouseDragged(MouseEvent e) {
        dragCurrent = e.getPoint();
        return true;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        // Select all elements within selection rect
        Rectangle rect = getSelectionRect();
        SVGElement[] elements = getView().getElementsInRect(rect);
        getView().setSelectedElements(elements);

        // Clear selection rectangle
        dragStart = dragCurrent;

        return true;
    }

    @Override
    public void paint(Graphics2D g) {
        Stroke restoreStroke = g.getStroke();
        g.setColor(Color.BLACK);
        g.setStroke(dashedStroke);
        g.draw(getSelectionRect());
        g.setStroke(restoreStroke);
    }

    private Rectangle getSelectionRect() {
        int x = Math.min(dragStart.x, dragCurrent.x);
        int y = Math.min(dragStart.y, dragCurrent.y);
        int width = Math.abs(dragStart.x - dragCurrent.x);
        int height = Math.abs(dragStart.y - dragCurrent.y);
        return new Rectangle(x, y, width, height);
    }

}
