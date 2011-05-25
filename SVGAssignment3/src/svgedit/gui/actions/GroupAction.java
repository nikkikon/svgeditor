package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import svgedit.commandManager.GroupCommand;
import svgedit.gui.Frame;
/** Combines selected elements into a group.
 */
@SuppressWarnings("serial")
public class GroupAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public GroupAction(Frame frame) {
        super("Group");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
    	GroupCommand gc = new GroupCommand(frame);
    	frame.getView().getCommandStack().addCommand(gc);
    	gc.execute();
    	/*
    	
        SVGElement elems[] = frame.getView().getSelectedElements();

        // Remove elems from their current parent and add to a new
        // group.
        // TODO: Z-ordering is broken by this operation; group should be
        //   inserted where the first child was
        if (elems.length > 1) {
            SVGGroup group = new SVGGroup(frame.getDocument());
            for (SVGElement elem : elems) {
                elem.getParent().removeChild(elem);
                group.appendChild(elem);
            }
            frame.getDocument().getRootGroup().appendChild(group);
            frame.getView().getDocument().setModified(true);
            frame.getView().setSelectedElement(group);
        }
        */
    }


}
