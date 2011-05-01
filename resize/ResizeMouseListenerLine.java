package svgedit.resize;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import svgedit.gui.ElementView;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;
import svgedit.svg.SVGLineElement;

public class ResizeMouseListenerLine extends ResizeMouseListener implements MouseListenerInterface{

	private ElementView c;
	private MouseInputListener resizeListener;
	private SVGLineElement line;
	private float newX1;
	private float newY1;
	private float newX2;
	private float newY2;
	private String newX1String;
	private String newY1String;
	private String newX2String;
	private String newY2String;
	private int dx,dy;
	public ResizeMouseListenerLine(ElementView c,ResizeableBorder border){
		this.c = c;
		addResizeMouseListener();
		line = (SVGLineElement) c.getSVGElement();
		newX1 = line.getX1().getValue();
		newX2 = line.getX2().getValue();
		newY1 = line.getY1().getValue();
		newY2 = line.getY2().getValue();
		newX1String = String.valueOf(line.getX1().getValueInSpecifiedUnits())+line.getX1().getUserUnit();
		newX2String = String.valueOf(line.getX2().getValueInSpecifiedUnits())+line.getX2().getUserUnit();
		newY1String = String.valueOf(line.getY1().getValueInSpecifiedUnits())+line.getY1().getUserUnit();
		newY2String = String.valueOf(line.getY2().getValueInSpecifiedUnits())+line.getY2().getUserUnit();
		dx = dy = 0;
	}
	@Override
	public MouseInputListener getListener(){
		return resizeListener;
	}
	
	public void resize() {
	    if (c.getParent() != null) {
	      ((JComponent)(c.getParent())).revalidate();
	    }
	}
	
	public void addResizeMouseListener() {
		  resizeListener = new MouseInputAdapter() {
			    public void mouseMoved(MouseEvent me) {
			    	
			      if (c.hasFocus()) {
			    	 // System.out.println(me.getComponent().getGraphics().getClass());
			    	  ResizeableBorderLine border = (ResizeableBorderLine)(c.getBorder());
			          c.setCursor(Cursor.getPredefinedCursor(border.getCursor(me)));
			          
			      }
			    }

			    public void mouseExited(MouseEvent mouseEvent) {
			       c.setCursor(Cursor.getDefaultCursor());
			    }

			    private int cursor;
			    private Point startPos = null;

			    public void mousePressed(MouseEvent me) {
			    	 
			    	ResizeableBorderLine border = (ResizeableBorderLine)(c.getBorder());
			      cursor = border.getCursor(me);
			      startPos = me.getPoint();
			      c.requestFocus();
			      c.repaint();
			      setisSelectedforJComponent(c);
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
			       newX1 = line.getX1().getValue();
		           newY1 = line.getY1().getValue();
		           newX2 = line.getX2().getValue();
		           newY2 = line.getY2().getValue();
			        switch (cursor) {
			          case Cursor.NW_RESIZE_CURSOR:
			            if (!(w - dx < 50) && !(h - dy < 50)) {
			              c.setBounds(x + dx, y + dy, w - dx, h - dy);
			              resize();
			              System.out.println(me.getX());
			              if(line.getX1().getValue()<line.getX2().getValue()||(line.getX1().getValue()==line.getX2().getValue()&&line.getY1().getValue()<line.getY2().getValue())){
			                  newX1 += dx;
			                  line.getX1().setValue(newX1);
			                  newY1 += dy;
			                  line.getY1().setValue(newY1);
			                  c.repaint();
			              }
			              if(line.getX1().getValue()>line.getX2().getValue()||(line.getX1().getValue()==line.getX2().getValue()&&line.getY1().getValue()>line.getY2().getValue())){
			            	  newX2 +=dx;
			            	  line.getX2().setValue(newX2);
			            	  newY2 +=dy;
			            	  line.getY2().setValue(newY2);
			            	  c.repaint();
			              }
			            }
			            break;

			          case Cursor.NE_RESIZE_CURSOR:
			            if (!(w + dx < 50) && !(h - dy < 50)) {
			              c.setBounds(x, y + dy, w + dx, h - dy);
			              startPos = me.getPoint();
			              resize();
			              if(line.getY1().getValue()<line.getY2().getValue()||(line.getY1().getValue()==line.getY2().getValue()&&line.getX1().getValue()>line.getX2().getValue())){
			                  newX1 += dx;
			                  line.getX1().setValue(newX1);
			                  newY1 += dy;
			                  line.getY1().setValue(newY1);
			                  c.repaint();
			              }
			              if(line.getY1().getValue()>line.getY2().getValue()||(line.getY1().getValue()==line.getY2().getValue()&&line.getX1().getValue()<line.getX2().getValue())){
			            	  newX2 +=dx;
			            	  line.getX2().setValue(newX2);
			            	  newY2 +=dy;
			            	  line.getY2().setValue(newY2);
			            	  c.repaint();
			              }
			            }
			            break;

			          case Cursor.SW_RESIZE_CURSOR:
			            if (!(w - dx < 50) && !(h + dy < 50)) {
			              c.setBounds(x + dx, y, w - dx, h + dy);
			              startPos = me.getPoint();
			              resize();
			              if(line.getY1().getValue()>line.getY2().getValue()||(line.getY1().getValue()==line.getY2().getValue()&&line.getX1().getValue()<line.getX2().getValue())){
			                  newX1 += dx;
			                  line.getX1().setValue(newX1);
			                  newY1 += dy;
			                  line.getY1().setValue(newY1);
			                  c.repaint();
			              }
			              if(line.getY1().getValue()<line.getY2().getValue()||(line.getY1().getValue()==line.getY2().getValue()&&line.getX1().getValue()>line.getX2().getValue())){
			            	  newX2 +=dx;
			            	  line.getX2().setValue(newX2);
			            	  newY2 +=dy;
			            	  line.getY2().setValue(newY2);
			            	  c.repaint();
			              }
			            }
			            break;

			          case Cursor.SE_RESIZE_CURSOR:
//					        int dx2 = me.getX()/100 - startPos.x;
//					        int dy2 = me.getY()/100 - startPos.y;
			            if (!(w + dx < 50) && !(h + dy < 50)) {
			              c.setBounds(x, y, w + dx, h + dy);
			              startPos = me.getPoint();
			              resize();
			              if(line.getX1().getValue()<line.getX2().getValue()||(line.getX1().getValue()==line.getX2().getValue()&&line.getY1().getValue()<line.getY2().getValue())){
			                  newX2 += dx;
			                  line.getX2().setValue(newX2);
			                  newY2 += dy;
			                  line.getY2().setValue(newY2);
			                  c.repaint();
			              }
			              if(line.getX1().getValue()>line.getX2().getValue()||(line.getX1().getValue()==line.getX2().getValue()&&line.getY1().getValue()>line.getY2().getValue())){
			            	  newX1 += dx;
			                  line.getX1().setValue(newX1);
			                  newY1 += dy;
			                  line.getY1().setValue(newY1);
			                  c.repaint();
			              }
			            }
			          break;

			          case Cursor.MOVE_CURSOR:
			            Rectangle bounds = c.getBounds();
			            bounds.translate(dx, dy);
			            c.setBounds(bounds);
			            resize();
			           
			            newX1 += dx;
			            newX2 += dx;
			            newY1 += dy;
			            newY2 += dy;
			            System.out.println("new NEWX1"+newX1); 
			            System.out.println("new NEWY1"+newY1);
			            line.getX1().setValue(newX1);
			            line.getX2().setValue(newX2);
			            line.getY1().setValue(newY1);
			            line.getY2().setValue(newY2);
			           
			            
			            
			            if(!c.getSVGElement().getParent().equals(c.getView().getFrame().getDocument().getRootGroup())) 
				           {
				        	   moveGroup(dx,dy);
				           }; 
			          }


			          c.setCursor(Cursor.getPredefinedCursor(cursor));
			        }
			     }

			   public void mouseReleased(MouseEvent mouseEvent) {
				   startPos = null;
				   newX1String = String.valueOf(line.getX1().getValueInSpecifiedUnits())+line.getX1().getUserUnit();
		            newX2String = String.valueOf(line.getX2().getValueInSpecifiedUnits())+line.getX2().getUserUnit();
		            newY1String = String.valueOf(line.getY1().getValueInSpecifiedUnits())+line.getY1().getUserUnit();
		            newY2String = String.valueOf(line.getY2().getValueInSpecifiedUnits())+line.getY2().getUserUnit();
			     c.getElement().setAttribute("x1", newX1String);
			     c.getElement().setAttribute("x2", newX2String);
			     c.getElement().setAttribute("y1", newY1String);
			     c.getElement().setAttribute("y2", newY2String);
			     
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
	            otherComponent.repaint();
	            otherComponent.getSVGElement().setOffset(dx, dy);
	            c.getView().repaint(); 
	            
	        } 
		   catch(Exception e){
			   continue;
		   }
	   }
			//System.out.println(iterator.next());
		}



}
