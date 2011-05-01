package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.AbstractAction;

import org.w3c.dom.Document;

import svgedit.gui.ElementView;
import svgedit.gui.Frame;
import svgedit.xml.XMLReader;

public class UngroupAction extends AbstractAction{
    
	private Frame frame;
	public UngroupAction(Frame frame){
		super("Ungroup");
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		for(int i = 0;i<frame.getJCVector().size();i++){
			if(frame.getJCVector().get(i).getisSelected()){
				frame.getJCVector().get(i).getGroup().removeChild(frame.getJCVector().get(i).getSVGElement());
				frame.getDocument().getRootGroup().appendChild(frame.getJCVector().get(i).getSVGElement());
				Document doc = XMLReader.getDocument();
				frame.getJCVector().get(i).getElement().getParentNode().removeChild(frame.getJCVector().get(i).getElement());
				doc.getDocumentElement().appendChild(frame.getJCVector().get(i).getElement());
			    frame.getView().repaint(); 
				//frame.writeFile();
			}
			try {
				frame.writeFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//frame.openFile(frame.getTempFile());
		}

		
	}

}
