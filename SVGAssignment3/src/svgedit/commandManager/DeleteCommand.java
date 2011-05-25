package svgedit.commandManager;

import java.util.ArrayList;

import svgedit.gui.Frame;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;

public class DeleteCommand implements CommandInterface{

	private Frame frame;
	private ArrayList<SVGElement> elements = new ArrayList<SVGElement>();
	private ArrayList<SVGGroup> groups = new ArrayList<SVGGroup>();
	public DeleteCommand(Frame frame){
		elements.clear();
		groups.clear();
		this.frame=frame;
	}
	public void execute(){
		if(elements.size() == 0){
			for (SVGElement elem : frame.getView().getSelectedElements()){
			    elements.add(elem);
			    groups.add(elem.getParent());
	            elem.getParent().removeChild(elem);
		 }
		 
		}else{
			for (SVGElement elem : elements){
				elem.getParent().removeChild(elem);
			}
		}
	        frame.getDocument().setModified(true);
	        frame.getView().clearSelection();
	        frame.repaint();
	        frame.setUndoEnable();
	}
	public void undo(){
		for(int i =0; i < elements.size(); i++){
			groups.get(i).appendChild(elements.get(i));
		}
		frame.repaint();
	}
}