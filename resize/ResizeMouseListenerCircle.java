package svgedit.resize;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import svgedit.gui.ElementView;
import svgedit.svg.SVGCircleElement;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;
import svgedit.svg.SVGIterator;
import svgedit.xml.XMLReader;





public class ResizeMouseListenerCircle extends ResizeMouseListener implements MouseListenerInterface{

	private ElementView c;
	private MouseInputListener resizeListener;
	private String newAttributeValue;
	private SVGCircleElement circle;
	private float newX;
	private float newY;
	private String newXString;
	private String newYString;
	private int dx;
	private int dy;
	private float oldX;
	private float oldY;
	public ResizeMouseListenerCircle(ElementView c,ResizeableBorder border){
		
		this.c = c;
		addResizeMouseListener();
		circle = (SVGCircleElement) c.getSVGElement();
		newAttributeValue = String.valueOf(circle.getR().getValueInSpecifiedUnits())+circle.getR().getUserUnit();
		oldX = newX = circle.getCX().getValue();
		oldY = newY = circle.getCY().getValue();
	    newXString = String.valueOf(circle.getCX().getValueInSpecifiedUnits())+circle.getCX().getUserUnit();
	    newYString = String.valueOf(circle.getCY().getValueInSpecifiedUnits())+circle.getCY().getUserUnit();
	    //System.out.println(newAttributeValue);
	    dx = 0;
	    dy = 0;
	}
	
	@Override
	public MouseInputListener getListener(){
		return resizeListener;
	}
	
	public void resize() {
	    if (c.getParent()!= null) {
	      ((JComponent)(c.getParent())).revalidate();
	      
	    }
	}
	
	public void addResizeMouseListener() {
		 
		  resizeListener = new MouseInputAdapter() {
			  
			    public void mouseMoved(MouseEvent me) {
			    	
			      if (c.hasFocus()) {
			    	  ResizeableBorderCircle border = (ResizeableBorderCircle)(c.getBorder());
			    	  c.setCursor(Cursor.getPredefinedCursor(border.getCursor(me)));
			       }
			    }

			    public void mouseExited(MouseEvent mouseEvent) {
			    	 c.setCursor(Cursor.getDefaultCursor());
			         
			       
			    }

			    private int cursor;
			    private Point startPos = null;

			    public void mousePressed(MouseEvent me) {
			     
					  ResizeableBorderCircle border = (ResizeableBorderCircle)(c.getBorder());
				      cursor = border.getCursor(me);
				      startPos = me.getPoint();
				      c.requestFocus();
	                  c.repaint();
	                  c.getView().repaint();
				  
			    }

			    public void mouseDragged(MouseEvent me) {

			      if (startPos != null) {

			        int x = c.getX();
			        int y = c.getY();
			        int w = c.getWidth();
			        int h = c.getHeight();

			        dx = me.getX() - startPos.x;
			        dy = me.getY() - startPos.y;
			        newX = circle.getCX().getValue();
		            newY = circle.getCY().getValue();
			        switch (cursor) {
			          case Cursor.N_RESIZE_CURSOR:
			            if (!(h - dy < 50)) {
			              //c.setBounds(x, y + dy, w, h - dy);
			              c.setBounds(x + dy, y + dy, w-2*dy, h - 2*dy);
			              resize();
			              
			              float newR = circle.getR().getValue()-dy;
			              circle.getR().setValue(newR);
			              c.repaint();
			             // newAttributeValue = String.valueOf(circle.getR().getValueInSpecifiedUnits())+circle.getR().getUserUnit();
			              
			            }
			            break;

                      case Cursor.MOVE_CURSOR:
			            Rectangle bounds = c.getBounds();
			            bounds.translate(dx, dy);
			            c.setBounds(bounds);
			            resize();
			            
			            newX +=dx;
			            newY +=dy;
			           
			            circle.getCX().setValue(newX);
			            circle.getCY().setValue(newY);
			            
			            //newXString = String.valueOf(circle.getCX().getValueInSpecifiedUnits())+circle.getCX().getUserUnit();
			            //newYString = String.valueOf(circle.getCY().getValueInSpecifiedUnits())+circle.getCY().getUserUnit();
			            if(!c.getSVGElement().getParent().equals(c.getView().getFrame().getDocument().getRootGroup())) 
				           {
				        	   moveGroup(dx,dy);
				           }; 
                       
                        
			          }


			          c.setCursor(Cursor.getPredefinedCursor(cursor));
			        }
			     }
				protected void moveGroup(int dx,int dy) {
					// TODO Auto-generated method stub
					
				   SVGGroup group =  c.getGroup();
				   SVGElement[] children = group.getChildren();
				
				   for(int i = 0;i<children.length;i++){
					   if(children[i].equals(c.getSVGElement())){
						    continue;
					   }
					   try{
						    ElementView otherComponent = (ElementView) children[i].getComponent();
							Rectangle bounds =otherComponent.getBounds();
				            bounds.translate(dx, dy);
				            otherComponent.setBounds(bounds);
				            otherComponent.getSVGElement().setOffset(dx, dy);
				        } 
					   catch(Exception e){
						   continue;
					   }
				   }
						//System.out.println(iterator.next());
					}
			   

			public void mouseReleased(MouseEvent mouseEvent) {
			     startPos = null;
			     newXString = String.valueOf(circle.getCX().getValueInSpecifiedUnits())+circle.getCX().getUserUnit();
		         newYString = String.valueOf(circle.getCY().getValueInSpecifiedUnits())+circle.getCY().getUserUnit();
		         newAttributeValue = String.valueOf(circle.getR().getValueInSpecifiedUnits())+circle.getR().getUserUnit();
		         System.out.println("LAST CLIK "+newXString );
			     c.getElement().setAttribute("r", newAttributeValue);
			     c.getElement().setAttribute("cx", newXString);
			     c.getElement().setAttribute("cy", newYString);
			     SVGGroup group =  c.getGroup();
				 SVGElement[] children = group.getChildren();
				 for(int i = 0;i<children.length;i++){
						   if(children[i].equals(c.getSVGElement())){
							    continue;
						   }
						   try{
							   ElementView otherComponent = (ElementView) children[i].getComponent();
							   otherComponent.getSVGElement().setOffsetBackToFile();
					       } 
						   catch(Exception e){
							   continue;
						   }
				 }
	            
			     try {
					c.getView().getFrame().writeFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			   }
			  };
	}

	@Override
	public SVGElement getSVGElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOffset(int dx, int dy) {
		// TODO Auto-generated method stub
		
	}
    
	

}
