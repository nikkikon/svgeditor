package svgedit.gui.actions;

import svgedit.gui.Frame;
import svgedit.gui.View;
import svgedit.gui.manipulators.InsertLineManipulator;
import svgedit.gui.manipulators.Manipulator;

/** Sets view manipulator to draw a new line element.
 *
 */
@SuppressWarnings("serial")
public class InsertLineElementAction extends InsertElementAction {

    public InsertLineElementAction(Frame frame) {
        super("Line", frame);
    }

    @Override
    protected Manipulator createManipulator(View view) {
        return new InsertLineManipulator(view);
    }
}
