package svgedit.gui.manipulators;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import svgedit.gui.View;
import svgedit.svg.SVGLength;
import svgedit.svg.SVGDocument;
import svgedit.svg.SVGRectElement;

/** Manipulator for drawing a rectangle.  The user clicks and drags within the view
 *  to define the top-left and bottom-right points of the rectangle.
 */
public class InsertRectManipulator extends Manipulator {

    private SVGRectElement rect;

    /** Creates a manipulator for drawing a rectangle within the given view.
     *
     * @param view the view this manipulator belongs to
     */
    public InsertRectManipulator(View view) {
        super(view);
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        rect = new SVGRectElement(getView().getDocument());
        rect.getX().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, e.getX());
        rect.getY().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, e.getY());
        rect.getWidth().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, 0.0f);
        rect.getHeight().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, 0.0f);

        rect.setStyleFromStylable(getView().getDefaultStyle());
        return true;
    }

    @Override
    public boolean mouseDragged(MouseEvent e) {
        rect.getWidth().setValue(e.getX() - rect.getX().getValue());
        rect.getHeight().setValue(e.getY() - rect.getY().getValue());
        return true;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        View view = getView();
        SVGDocument document = view.getDocument();
        document.getRootGroup().appendChild(rect);
        document.setModified(true);
        view.setSelectedElement(rect);
        view.setSelectionManipulator();
        rect = null;
        return true;
    }

    @Override
    public void paint(Graphics2D g) {
        if (rect != null)
            getView().paintElement(g, rect);
    }

    @Override
    public Cursor getCursor(Point point) {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

}
