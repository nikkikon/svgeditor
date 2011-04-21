package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import svgedit.gui.Frame;
import svgedit.gui.SVGFileFilter;

/** Shows discard changes dialog, then presents a file dialog for the
 *  user to select an existing document to edit.
 */
@SuppressWarnings("serial")
public class OpenDocumentAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public OpenDocumentAction(Frame frame) {
        super("Open...");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        String directory = frame.getPreferences().getDefaultPath();
        JFileChooser dialog = new JFileChooser(directory);
        dialog.addChoosableFileFilter(new SVGFileFilter());
        dialog.showOpenDialog(null);

        File file = dialog.getSelectedFile();
        if (file != null) {
            frame.getPreferences().setDefaultPath(file.getPath());
            frame.openFile(file);
        }
    }


}
