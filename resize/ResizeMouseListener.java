package svgedit.resize;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import svgedit.gui.ElementView;

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
	

}

