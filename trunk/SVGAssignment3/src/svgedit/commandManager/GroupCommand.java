package svgedit.commandManager;

import java.util.ArrayList;

import svgedit.gui.Frame;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;

public class GroupCommand implements CommandInterface{

	private Frame frame;
	private SVGGroup newGroup;
	private SVGElement [] elems;
//	private ArrayList<SVGGroup> groups = new ArrayList<SVGGroup>();
	public GroupCommand(Frame frame){
		//elements.clear();
		this.frame=frame;
	}
	public void execute(){
		elems = frame.getView().getSelectedElements();

        // Remove elems from their current parent and add to a new
        // group.
        // TODO: Z-ordering is broken by this operation; group should be
        //   inserted where the first child was
        if (elems.length > 1) {
            SVGGroup group = new SVGGroup(frame.getDocument());
            for (SVGElement elem : elems) {
         //   	elements.add(elem);
                elem.getParent().removeChild(elem);
                group.appendChild(elem);
            }
            frame.getDocument().getRootGroup().appendChild(group);
            frame.getView().getDocument().setModified(true);
            System.out.println("tttttt");
            frame.getView().setSelectedElement(group);
            newGroup = group;
        }	
        frame.setUndoEnable();
	}
	public void undo(){

        // New selection will be previous selection, plus all children
        // that were ungrouped
        ArrayList<SVGElement> selection = new ArrayList<SVGElement>();

        // Get the new added group 

        // Remove children from group, add to document root
        // TODO: Z-ordering is broken by this operation;
        //   children should be inserted where the group was
        SVGElement[] children = ((SVGGroup) newGroup).getChildren();
        for (SVGElement child : children) {
            child.getParent().removeChild(child);
            frame.getDocument().getRootGroup().appendChild(child);
            selection.add(child);
        }

        // Remove group
        newGroup.getParent().removeChild(newGroup);            
        frame.getView().getDocument().setModified(true);
        frame.getView().setSelectedElements(selection.toArray(new SVGElement[0]));
	}
}