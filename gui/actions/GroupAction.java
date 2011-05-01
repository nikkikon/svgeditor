package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import svgedit.gui.Frame;
import svgedit.svg.SVGGroup;
import svgedit.xml.XMLReader;

public class GroupAction extends AbstractAction{

	private Frame frame;
    public GroupAction(Frame frame){
    	super("Group");
    	this.frame = frame;
    	
    }
	 @Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 Document doc = XMLReader.getDocument();
		 SVGGroup group = new SVGGroup(frame.getDocument());
		 
		 Node newGroupNode = doc.createElement("g");
		 for(int i = 0;i<frame.getJCVector().size();i++){
				if(frame.getJCVector().get(i).getisSelected()){
					group.appendChild(frame.getJCVector().get(i).getSVGElement());
					frame.getJCVector().get(i).getElement().getParentNode().removeChild(frame.getJCVector().get(i).getElement());
					newGroupNode.appendChild(frame.getJCVector().get(i).getElement());
					
				}
			}
		 doc.getDocumentElement().appendChild(newGroupNode);
			try {
				frame.writeFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//frame.openFile(frame.getTempFile());
		
	}

}
