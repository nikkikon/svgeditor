package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import org.w3c.dom.Document;

import svgedit.gui.DocumentPropertyEditBox;
import svgedit.gui.Frame;
import svgedit.svg.SVGLength;
import svgedit.xml.XMLReader;

public class ConfirmAction extends AbstractAction{

	private DocumentPropertyEditBox frame;
	private SVGLength svgLength;
	public ConfirmAction(DocumentPropertyEditBox documentPropertyEditBox){
		super("Confirm");
		this.frame = documentPropertyEditBox;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("ConfirmAction");
		SVGLength width = frame.getFrame().getDocument().getWidth();
		width.setValueFromString(frame.getNewWidth());
		width.printUnit();
		
		SVGLength heigth = frame.getFrame().getDocument().getHeight();
		heigth.setValueFromString(frame.getNewHeigth());
		heigth.printUnit();
		System.out.println(XMLReader.getsvgRootElement().getElement().getAttribute("width"));
		XMLReader.getsvgRootElement().getElement().setAttribute("width", frame.getNewWidth());
		XMLReader.getsvgRootElement().getElement().setAttribute("height", frame.getNewHeigth());
		
		frame.dispose();
		frame.getFrame().repaint();
		try {
			frame.getFrame().writeFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
