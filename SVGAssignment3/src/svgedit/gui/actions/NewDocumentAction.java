package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

/** Shows discard changes dialog, then sets the document to a new,
 *  empty document.
 */
@SuppressWarnings("serial")
public class NewDocumentAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public NewDocumentAction(Frame frame) {
        super("New");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        if (frame.confirmSaveChanges())
            frame.newFile();
    }


}
