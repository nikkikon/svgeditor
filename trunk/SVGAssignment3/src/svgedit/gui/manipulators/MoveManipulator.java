package svgedit.gui.manipulators;

import java.awt.*;
import java.awt.event.*;

import svgedit.svg.SVGElement;
import svgedit.gui.View;
import svgedit.svg.SVGCircleElement;
import svgedit.svg.SVGGroup;
import svgedit.svg.SVGLineElement;
import svgedit.svg.SVGRectElement;
import svgedit.svg.SVGVisitor;

/** Manipulator for moving one or more elements.  The user can click and drag
 *  within any of the selected elements to move their position.
 */
public class MoveManipulator extends Manipulator {

    private SVGElement[] elements;
    private Point lastPoint;

    /** Constructs a manipulator within the given view that can move the given
     *  elements.
     *
     * @param view the view this manipulator belongs to
     * @param elements the elements to move
     */
    public MoveManipulator(View view, SVGElement[] elements) {
        super(view);
        this.elements = elements;
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        for (SVGElement element : elements) {
            if (getView().elementContainsPoint(element, e.getPoint())) {
                lastPoint = e.getPoint();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseDragged(MouseEvent e) {
        float dx = (float) (e.getX() - lastPoint.getX());
        float dy = (float) (e.getY() - lastPoint.getY());

        MoveElementVisitor visitor = new MoveElementVisitor(dx, dy);
        for (SVGElement element : elements) {
            element.acceptVisitor(visitor);
        }

        getView().getDocument().setModified(true);
        lastPoint = e.getPoint();
        return true;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        return true;
    }

    /** Visitor implementing the move logic for each element type. */
    private static class MoveElementVisitor implements SVGVisitor {

        private float dx, dy;

        public MoveElementVisitor(float dx, float dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public void visitGroup(SVGGroup group) {
            for (SVGElement child : group)
                child.acceptVisitor(this);
        }

        public void visitRect(SVGRectElement rect) {
            rect.getX().setValue(rect.getX().getValue() + dx);
            rect.getY().setValue(rect.getY().getValue() + dy);
        }

        public void visitCircle(SVGCircleElement circle) {
            circle.getCX().setValue(circle.getCX().getValue() + dx);
            circle.getCY().setValue(circle.getCY().getValue() + dy);
        }

        public void visitLine(SVGLineElement line) {
            line.getX1().setValue(line.getX1().getValue() + dx);
            line.getY1().setValue(line.getY1().getValue() + dy);
            line.getX2().setValue(line.getX2().getValue() + dx);
            line.getY2().setValue(line.getY2().getValue() + dy);
        }

    }

}
