package svgedit.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import svgedit.gui.Frame;

public class SelectAllAction extends AbstractAction{

	private Frame frame;
	public SelectAllAction(Frame frame){
		super("Select All");
	    this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i = 0;i<frame.getJCVector().size();i++){
			  if(frame.getJCVector().get(i).getSVGElement().getParent().equals(frame.getDocument().getRootGroup())){
					//System.out.println("INTERSECT"); 
				 
					frame.getJCVector().get(i).setisIntersect(true);
					//System.out.println(frame.getJCVector().get(i).getisSelected());
					frame.getJCVector().get(i).repaint();
					
				 }
			 }
		System.out.println("Select All");
	}

}
