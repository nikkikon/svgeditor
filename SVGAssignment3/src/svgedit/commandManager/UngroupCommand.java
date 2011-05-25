package svgedit.commandManager;

import java.util.ArrayList;

import svgedit.gui.Frame;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;

public class UngroupCommand implements CommandInterface{

	private Frame frame;
	private SVGElement [] elems;
	private ArrayList<SVGElement> oldGroups = new ArrayList<SVGElement>();
	private ArrayList<SVGElement> oldChildren = new ArrayList<SVGElement>();
	
//	private ArrayList<SVGGroup> groups = new ArrayList<SVGGroup>();
	public UngroupCommand(Frame frame){
		//elements.clear();
		this.frame=frame;
	}
	public void execute(){
		elems = frame.getView().getSelectedElements();

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
                	//backup the deleted groups and their children
                	oldChildren.add(child);
                	oldGroups.add(child.getParent());
                	
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
        frame.setUndoEnable();
	}
	public void undo(){

        for(int i=0;i<oldGroups.size();i++){
        	((SVGGroup)(oldGroups.get(i))).appendChild(oldChildren.get(i));
        }
        for(int i=0;i<oldGroups.size();i++){
        	frame.getDocument().getRootGroup().appendChild(oldGroups.get(i));
            frame.getView().getDocument().setModified(true);
            frame.getView().setSelectedElement(oldGroups.get(i));
        }
        
	}
}