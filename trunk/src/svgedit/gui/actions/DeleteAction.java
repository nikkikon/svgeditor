package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;

/** Deletes all selected elements.
 *
 */
@SuppressWarnings("serial")
public class DeleteAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public DeleteAction(Frame frame) {
        super("Delete");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        for (SVGElement elem : frame.getView().getSelectedElements())
            elem.getParent().removeChild(elem);
        frame.getDocument().setModified(true);
        frame.getView().clearSelection();
        frame.repaint();
    }

}
