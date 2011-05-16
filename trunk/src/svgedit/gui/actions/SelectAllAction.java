package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

/** Selects all top-level elements in the document.
 */
@SuppressWarnings("serial")
public class SelectAllAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public SelectAllAction(Frame frame) {
        super("Select All");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        frame.getView().setSelectedElements(frame.getDocument().getRootGroup().getChildren());
    }


}
