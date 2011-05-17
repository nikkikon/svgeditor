package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;
import svgedit.gui.View;
import svgedit.gui.manipulators.Manipulator;

/** Sets manipulator to begin drawing a new element.
 *
 */
@SuppressWarnings("serial")
public abstract class InsertElementAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public InsertElementAction(String name, Frame frame) {
        super(name);
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        Manipulator manipulator = createManipulator(frame.getView());
        frame.getView().setManipulator(manipulator);
        frame.setManipulatorAction(this);
    }

    protected abstract Manipulator createManipulator(View view);

}
