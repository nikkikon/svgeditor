package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

/** Shows confirm changes dialog, then exits the application.
 */
@SuppressWarnings("serial")
public class QuitAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public QuitAction(Frame frame) {
        super("Quit");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        if (frame.confirmSaveChanges())
            System.exit(0);
    }

}
