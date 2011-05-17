package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import svgedit.gui.Frame;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;

/** Finds selected group elements and moves their children into the root
 *  group, deleting the group elements.
 */
@SuppressWarnings("serial")
public class UngroupAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public UngroupAction(Frame frame) {
        super("Ungroup");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        SVGElement elems[] = frame.getView().getSelectedElements();

        // New selection will be previous selection, plus all children
        // that were ungrouped
        ArrayList<SVGElement> selection = new ArrayList<SVGElement>();

        // Find currently selected groups
        for (SVGElement elem : elems) {
            if (elem instanceof SVGGroup) {
                // Remove children from group, add to document root
                // TODO: Z-ordering is broken by this operation;
                //   children should be inserted where the group was
                SVGElement[] children = ((SVGGroup) elem).getChildren();
                for (SVGElement child : children) {
                    child.getParent().removeChild(child);
                    frame.getDocument().getRootGroup().appendChild(child);
                    selection.add(child);
                }

                // Remove group
                elem.getParent().removeChild(elem);
            } else {
                selection.add(elem);
            }
        }

        frame.getView().getDocument().setModified(true);
        frame.getView().setSelectedElements(selection.toArray(new SVGElement[0]));
    }
}
