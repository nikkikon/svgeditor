package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;

/** Saves the document to the same file it was most recently saved/loaded
 *  from.
 */
@SuppressWarnings("serial")
public class SaveDocumentAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public SaveDocumentAction(Frame frame) {
        super("Save");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        // If there's no file yet, show the save as dialog
        if (frame.getDocument().getFile() == null)
            new SaveDocumentAsAction(frame).actionPerformed(ae);
        else
            frame.saveFile(frame.getDocument().getFile());
    }


}
