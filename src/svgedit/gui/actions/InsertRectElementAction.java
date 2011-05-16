package svgedit.gui.actions;

import svgedit.gui.Frame;
import svgedit.gui.View;
import svgedit.gui.manipulators.InsertRectManipulator;
import svgedit.gui.manipulators.Manipulator;

/** Sets view manipulator to draw a new rectangle element.
 *
 */
@SuppressWarnings("serial")
public class InsertRectElementAction extends InsertElementAction {

    public InsertRectElementAction(Frame frame) {
        super("Rectangle", frame);
    }

    @Override
    protected Manipulator createManipulator(View view) {
        return new InsertRectManipulator(view);
    }
}
