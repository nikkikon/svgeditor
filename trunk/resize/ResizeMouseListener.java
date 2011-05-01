package svgedit.resize;

import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import svgedit.gui.ElementView;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;

public class ResizeMouseListener {
	
	private String shape;
	private ElementView c;
	private ResizeableBorder rb;
	private int lineType;
	public ResizeMouseListener(ElementView c,String shape,int lineType){
		this.c = c;
		this.shape = shape;
		this.lineType = lineType;
	};
	public ResizeMouseListener(){
		
	};
	
	public MouseInputListener getListener(){
		if(shape.equals("Rectangle")){
			rb = new ResizeableBorderRectangle(8);
			return new ResizeMouseListenerRect(c,rb).getListener();
		}
		else if(shape.equals("Line")){
			rb = new ResizeableBorderLine(8,lineType);
			return new ResizeMouseListenerLine(c,rb).getListener();
		}
		else if(shape.equals("Circle")){
			rb = new ResizeableBorderCircle(8);
			return new ResizeMouseListenerCircle(c,rb).getListener();
		}
		return null;
	}
	
	public ResizeableBorder getBorder(){
		return rb;
	}
	
public void setisSelectedforJComponent(ElementView c){
	c.setisSelected(true);
    for(int i =0;i<c.getView().getFrame().getJCVector().size();i++){
  	  if(!(c.getView().getFrame().getJCVector().get(i).equals(c))){
  		  c.getView().getFrame().getJCVector().get(i).setisSelected(false);
  	  }
    }
}



}

