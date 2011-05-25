package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import svgedit.commandManager.DeleteCommand;
import svgedit.gui.Frame;

/** Deletes all selected elements.
 *
 */
@SuppressWarnings("serial")
public class DeleteAction extends AbstractAction {

    private Frame frame;
    private DeleteCommand deleteCommand;

    /** Create this action for the given frame */
    public DeleteAction(Frame frame) {
        super("Delete");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
    	deleteCommand = new DeleteCommand(frame);
        frame.getView().getCommandStack().addCommand(deleteCommand);
        deleteCommand.execute();
        //for (SVGElement elem : frame.getView().getSelectedElements())
            //elem.getParent().removeChild(elem);
        //frame.getDocument().setModified(true);
        //frame.getView().clearSelection();
        //frame.repaint();
    }

}
