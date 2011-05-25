package svgedit.gui.manipulators;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import svgedit.commandManager.InsertCommand;
import svgedit.gui.View;
import svgedit.svg.SVGLength;
import svgedit.svg.SVGLineElement;
import svgedit.svg.SVGPaint;

/** Manipulator for drawing a line.  The user clicks and drags within the view
 *  to define the two end points of the line
 */
public class InsertLineManipulator extends Manipulator {

    private SVGLineElement line;
    private InsertCommand insertCommand;

    /** Creates a manipulator for drawing a line within the given view.
     *
     * @param view the view this manipulator belongs to
     */
    public InsertLineManipulator(View view) {
        super(view);
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        line = new SVGLineElement(getView().getDocument());
        line.getX1().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, e.getX());
        line.getY1().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, e.getY());
        line.getX2().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, e.getX());
        line.getY2().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, e.getY());

        line.setStyleFromStylable(getView().getDefaultStyle());

        // No point drawing a line with no stroke; set one now if the default
        // isn't set.
        if (line.getStroke().getPaintType() == SVGPaint.SVG_PAINTTYPE_NONE)
            line.getStroke().setRGBColor(Color.BLACK);
        if (line.getStrokeWidth().getValue() <= 0.0f)
            line.getStrokeWidth().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, 1.0f);

        return true;
    }

    @Override
    public boolean mouseDragged(MouseEvent e) {
        line.getX2().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, e.getX());
        line.getY2().newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, e.getY());
        return true;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        View view = getView();
        //document.getRootGroup().appendChild(line);
        //document.setModified(true);
       // view.setSelectedElement(line);
        //view.setSelectionManipulator();
        //
        insertCommand = new InsertCommand(view, line);
        view.getCommandStack().addCommand(insertCommand);
        insertCommand.execute();
        line = null;
        return true;
    }

    @Override
    public void paint(Graphics2D g) {
        if (line != null)
            getView().paintElement(g, line);
    }

    @Override
    public Cursor getCursor(Point point) {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

}
