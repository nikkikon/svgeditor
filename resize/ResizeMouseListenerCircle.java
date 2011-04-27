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
	
	public ResizeMouseListenerCircle(ElementView c,ResizeableBorder border){
		this.c = c;
		addResizeMouseListener();
		circle = (SVGCircleElement) c.getSVGElement();
		newAttributeValue = String.valueOf(circle.getR().getValueInSpecifiedUnits());
	    newX = circle.getCX().getValue();
	    newY = circle.getCY().getValue();
	    
	    newXString = String.valueOf(circle.getCX().getValueInSpecifiedUnits());
	    newYString = String.valueOf(circle.getCY().getValueInSpecifiedUnits());
	    //System.out.println(newAttributeValue);
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

			        int dx = me.getX() - startPos.x;
			        int dy = me.getY() - startPos.y;
			 
			        switch (cursor) {
			          case Cursor.N_RESIZE_CURSOR:
			            if (!(h - dy < 50)) {
			              //c.setBounds(x, y + dy, w, h - dy);
			              c.setBounds(x + dy, y + dy, w-2*dy, h - 2*dy);
			              resize();
			              
			              float newR = circle.getR().getValue()-dy;
			              circle.getR().setValue(newR);
			              c.repaint();
			              newAttributeValue = String.valueOf(circle.getR().getValueInSpecifiedUnits())+circle.getR().getUserUnit();
			              
			            }
			            break;
/*
			          case Cursor.S_RESIZE_CURSOR:
			           if (!(h + dy < 50)) {
			                c.setBounds(x, y, w, h + dy);
			            	//c.setBounds(x - dy, y - dy, w+2*dy, h + 2*dy);
			            	startPos = me.getPoint();
			              resize();
			            }
			            break;

			          case Cursor.W_RESIZE_CURSOR:
			            if (!(w - dx < 50)) {
			              c.setBounds(x + dx, y, w - dx, h);
			              resize();
			            }
			            break;

			          case Cursor.E_RESIZE_CURSOR:
			            if (!(w + dx < 50)) {
			              c.setBounds(x, y, w + dx, h);
			              startPos = me.getPoint();
			              resize();
			            }
	            break;
	            */		
                      case Cursor.MOVE_CURSOR:
			            Rectangle bounds = c.getBounds();
			            bounds.translate(dx, dy);
			            c.setBounds(bounds);
			            resize();
			             
			            newX +=dx;
			            newY +=dy;
			            circle.getCX().setValue(newX);
			            circle.getCY().setValue(newY);
			            
			            newXString = String.valueOf(circle.getCX().getValueInSpecifiedUnits());
			            newYString = String.valueOf(circle.getCY().getValueInSpecifiedUnits());
			            

			          }


			          c.setCursor(Cursor.getPredefinedCursor(cursor));
			        }
			     }

			   public void mouseReleased(MouseEvent mouseEvent) {
			     startPos = null;
			     c.getElement().setAttribute("r", newAttributeValue);
			     c.getElement().setAttribute("cx", newXString);
			     c.getElement().setAttribute("cy", newYString);
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

}
