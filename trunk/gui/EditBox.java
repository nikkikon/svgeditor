package svgedit.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import svgedit.gui.actions.EditBoxOKAction;

public class EditBox extends JFrame{

	public JTextField fillColorField;
	public JTextField strokeColorField;
	public JTextField strokeWidthField;
	private EditBoxOKAction editBoxOKAction;
	private Frame frame;
	private ElementView ev;
	
	public EditBox(Frame frame, ElementView ev){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("EditBox");
		this.frame = frame;
		this.ev = ev;
		fillColorField = new JTextField("Default");
		strokeColorField = new JTextField("Default");
		strokeWidthField = new JTextField("Default");
		editBoxOKAction = new EditBoxOKAction(this);
		
		setLayout(new GridLayout(4,2));
		add(new JLabel("Fill Color"));
		add(fillColorField);
		add(new JLabel("Stroke Color"));
		add(strokeColorField);
		add(new JLabel("strokeWidthField"));
		add(strokeWidthField);
		add(new JButton(editBoxOKAction));
		setSize(300,200);
	    show();
		
	}
    public void getFill(String rectFill){
		
		fillColorField.setText(rectFill);
	}
	
	public void getStroke(String rectStroke){
		strokeColorField.setText(rectStroke);
	}
	
	public void getStrokeWidth(String rectStrokeWidth){
		strokeWidthField.setText(rectStrokeWidth);
	}
	
	public ElementView getEV(){
		return ev;
	}
	
}
