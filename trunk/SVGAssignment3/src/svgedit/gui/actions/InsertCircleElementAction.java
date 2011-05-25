package svgedit.gui.actions;

import svgedit.gui.Frame;
import svgedit.gui.View;
import svgedit.gui.manipulators.InsertCircleManipulator;
import svgedit.gui.manipulators.Manipulator;

/** Sets view manipulator to draw a new circle element.
 *
 */
@SuppressWarnings("serial")
public class InsertCircleElementAction extends InsertElementAction {

    public InsertCircleElementAction(Frame frame) {
        super("Circle", frame);
    }

    @Override
    protected Manipulator createManipulator(View view) {
    	
        return new InsertCircleManipulator(view);
    }
}
