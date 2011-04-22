package svgedit.gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EditBox extends JFrame{

	private JTextField fillColorField;
	private JTextField strokeColorField;
	private JTextField strokeWidthField;
	
	public EditBox(Frame frame){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("EditBox");
		
		fillColorField = new JTextField("Default");
		strokeColorField = new JTextField("Default");
		strokeWidthField = new JTextField("Default");
		
		
		setLayout(new GridLayout(3,2));
		add(new JLabel("Fill Color"));
		add(fillColorField);
		add(new JLabel("Stroke Color"));
		add(strokeColorField);
		add(new JLabel("strokeWidthField"));
		add(strokeWidthField);
		
		setSize(300,200);
	    show();
		
	}
	
	
}
