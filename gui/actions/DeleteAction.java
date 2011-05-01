package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import org.w3c.dom.Document;

import svgedit.gui.Frame;
import svgedit.xml.XMLReader;

public class DeleteAction extends AbstractAction{

	private Frame frame;
	public DeleteAction(Frame frame){
		super("Delete");
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i = 0;i<frame.getJCVector().size();i++){
			if(frame.getJCVector().get(i).getisSelected()){
				frame.getJCVector().get(i).getSVGElement().getParent().removeChild(frame.getJCVector().get(i).getSVGElement());
				frame.getJCVector().get(i).getElement().getParentNode().removeChild(frame.getJCVector().get(i).getElement());
			}
		}
		try {
			frame.writeFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.openFile(frame.getTempFile());
		System.out.println("DELETE");
	}

}
