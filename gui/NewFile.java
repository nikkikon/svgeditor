package svgedit.gui;


import java.awt.GridLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import svgedit.gui.actions.CancelAction;
import svgedit.gui.actions.FinishAction;

public class NewFile extends JFrame{

	private Frame frame;
	private JTextField newFileName;
	private FinishAction finishAction;
	private CancelAction cancelAction;
	
	public NewFile(Frame frame){
		this.frame = frame;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("New File");
		setLayout(new GridLayout(2,2));
		
		newFileName = new JTextField();
		cancelAction = new CancelAction(this);
		finishAction = new FinishAction(this,frame);
		add(new JLabel("New File Name"));
		add(newFileName);
		add(new JButton(finishAction));
		add(new JButton(cancelAction));
		
	    setSize(300,200);
	    show();
	}
	
	public String getNewFileName(){
		return newFileName.getText();
		
	}
}
