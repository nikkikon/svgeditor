package svgedit.gui.manipulators;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import svgedit.commandManager.InsertCommand;
import svgedit.gui.View;
import svgedit.svg.SVGCircleElement;
import svgedit.svg.SVGDocument;
import svgedit.svg.SVGLength;

/** Manipulator for drawing a circle.  The user clicks and drags within the view
 *  to define a circle's center point and radius.
 */
public class InsertCircleManipulator extends Manipulator {

    private SVGCircleElement circle;
    private InsertCommand insertCommand;

    /** Creates a manipulator for drawing a circle within the given view.
     *
     * @param view the view this manipulator belongs to
     */
    public InsertCircleManipulator(View view) {
        super(view);
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        circle = new SVGCircleElement(getView().getDocument());
        circle.getCX().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, e.getX());
        circle.getCY().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, e.getY());
        circle.getR().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, 0.0f);

        circle.setStyleFromStylable(getView().getDefaultStyle());
        return true;
    }

    @Override
    public boolean mouseDragged(MouseEvent e) {
        circle.getR().setValue(e.getX() - circle.getCX().getValue());
        return true;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        View view = getView();
        SVGDocument document = view.getDocument();
        //document.getRootGroup().appendChild(circle);
        //document.setModified(true);
       //view.setSelectedElement(circle);
       // view.setSelectionManipulator();
        
        insertCommand = new InsertCommand(view, circle);
        view.getCommandStack().addCommand(insertCommand);
        insertCommand.execute();
        circle = null;
        return true;
    }

    @Override
    public void paint(Graphics2D g) {
        if (circle != null)
            getView().paintElement(g, circle);
    }

    @Override
    public Cursor getCursor(Point point) {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

}
