package svgedit.commandManager;

import svgedit.gui.View;
import svgedit.svg.SVGDocument;
import svgedit.svg.SVGElement;

public class InsertCommand implements CommandInterface{

	private View view;
	private SVGElement insertedElement;
	private SVGElement element;
	private SVGDocument document;
	public InsertCommand(View view, SVGElement element){
		this.view = view;
		this.insertedElement = element;
		document = view.getDocument();
	}
	public void execute(){
        document.getRootGroup().appendChild(insertedElement);
        element = insertedElement;
       // System.out.println(insertedElement.getParent());
        document.setModified(true);
        view.setSelectedElement(insertedElement);
        view.setSelectionManipulator();
        insertedElement = null;
	}
	
	public void undo(){
		insertedElement = element;
		view.clearSelection();
		document.getRootGroup().removeChild(insertedElement);
		view.repaint();
	}
}
