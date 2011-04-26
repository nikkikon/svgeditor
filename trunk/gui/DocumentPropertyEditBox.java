package svgedit.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import svgedit.gui.actions.CancelAction;
import svgedit.gui.actions.ConfirmAction;
import svgedit.xml.XMLReader;

public class DocumentPropertyEditBox extends JFrame{
             
	private ConfirmAction confirmAction;
	private CancelAction cancelAction;
	private JTextField widthField;
	private JTextField heigthField;
	private Frame frame;
	
	public DocumentPropertyEditBox(Frame frame){
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	     this.frame = frame;
		 setTitle("Document Property Edit Box");
		 confirmAction = new ConfirmAction(this);
		 cancelAction = new CancelAction(this);	 
		 widthField = new JTextField(Float.toString(frame.getDocument().getWidth().getValue()));
		 heigthField = new JTextField(Float.toString(frame.getDocument().getHeight().getValue()));
		 
		 setLayout(new GridLayout(3,2));
		 
		 
		 add(new JLabel("Width"));
		 add(widthField);
		 add(new JLabel("Heigth"));
	     add(heigthField);
	     add(new JButton(confirmAction));
	     add(new JButton(cancelAction));
	     
	     setSize(300,200);
	     show();
	}
	public String getNewWidth(){
		return widthField.getText();
	}
	public String getNewHeigth(){
		return heigthField.getText();
	}
	public Frame getFrame(){
		return frame;
	}
}
