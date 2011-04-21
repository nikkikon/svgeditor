package svgedit.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DocumentPropertyEditBox extends JFrame{
             
	//private ConfirmAction confirmAction;
	//private CancelAction cancelAction;
	private JTextField widthField;
	private JTextField heigthField;
	
	
	public DocumentPropertyEditBox(Frame frame){
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
	     setTitle("Document Property Edit Box");
		 //confirmAction = new ConfirmAction(this);
		 //cancelAction = new CancelAction(this);	 
		 widthField = new JTextField("DEFAULT");
		 heigthField = new JTextField("DEFAULT");
	     
		 add(new JLabel("Width"));
		 add(widthField);
		 add(new JLabel("Heigth"));
	     add(heigthField);
	     add(new JButton("confirmAction"));
	     add(new JButton("cancelAction"));
	     
	     setSize(300,200);
	     show();
	}
}
