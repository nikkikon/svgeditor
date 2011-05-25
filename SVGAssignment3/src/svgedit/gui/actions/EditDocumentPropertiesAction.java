package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.DocumentPropertiesDialog;
import svgedit.gui.Frame;

/** Shows the document properties dialog.
 */
@SuppressWarnings("serial")
public class EditDocumentPropertiesAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public EditDocumentPropertiesAction(Frame frame) {
        super("Document Properties...");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        DocumentPropertiesDialog dlg = new DocumentPropertiesDialog(frame.getDocument(),frame);
        dlg.setModal(true);
        dlg.setLocationRelativeTo(frame);
        dlg.setVisible(true);
        frame.repaint();
    }

}
