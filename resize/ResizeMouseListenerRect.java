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
import svgedit.svg.SVGCircleElement;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;
import svgedit.svg.SVGRectElement;
import svgedit.xml.XMLReader;
import svgedit.xml.XMLUtil;



public class ResizeMouseListenerRect extends ResizeMouseListener implements MouseListenerInterface{

	
	private ElementView c;
	private MouseInputListener resizeListener;
	private float newX;
	private float newY;
	private String newXString;
	private String newYString;
	private int dx;
	private int dy;
	private float newW;
	private float newH;
	private float oldX;
	private float oldY;
	private SVGRectElement rect;
	private String newHeightValue;
	private String newWidthValue;
	public ResizeMouseListenerRect(ElementView c,ResizeableBorder border){
		this.c = c;
		addResizeMouseListener();
		rect = (SVGRectElement) c.getSVGElement();
		newHeightValue = String.valueOf(rect.getHeight().getValueInSpecifiedUnits())+rect.getHeight().getUserUnit();
		newWidthValue = String.valueOf(rect.getWidth().getValueInSpecifiedUnits())+rect.getWidth().getUserUnit();
		oldX = newX = rect.getX().getValue();
		oldY = newY = rect.getY().getValue();
		newXString = String.valueOf(rect.getX().getValueInSpecifiedUnits())+rect.getX().getUserUnit();
		newYString = String.valueOf(rect.getY().getValueInSpecifiedUnits())+rect.getY().getUserUnit();
		newW = rect.getWidth().getValue();
		newH = rect.getHeight().getValue();    
	}
	
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
			    	  ResizeableBorderRectangle border = (ResizeableBorderRectangle)(c.getBorder());
			          c.setCursor(Cursor.getPredefinedCursor(border.getCursor(me)));
			          
			      }
			    }

			    public void mouseExited(MouseEvent mouseEvent) {
			       c.setCursor(Cursor.getDefaultCursor());
			    }

			    private int cursor;
			    private Point startPos = null;

			    public void mousePressed(MouseEvent me) {
			      ResizeableBorderRectangle border = (ResizeableBorderRectangle)(c.getBorder());
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

			        int dx = me.getX() - startPos.x;
			        int dy = me.getY() - startPos.y;
			        newX = rect.getX().getValue();
		            newY = rect.getY().getValue();
		            
		            newW = rect.getWidth().getValue();
		    		newH = rect.getHeight().getValue();    
			        switch (cursor) {
			          case Cursor.N_RESIZE_CURSOR:
			        	  if (!(h - dy < 50)) {
				              c.setBounds(x, y + dy, w, h - dy);
				              resize();
				              newY += dy;
				              rect.getY().setValue(newY);
				              newH -= dy;
				              rect.getHeight().setValue(newH);
				              c.repaint();
				             // newHeightValue = String.valueOf(rect.getHeight().getValueInSpecifiedUnits())+rect.getHeight().getUserUnit();
				            //  newYString = String.valueOf(rect.getY().getValueInSpecifiedUnits())+rect.getY().getUserUnit();
				          	
				            }
			            break;

			          case Cursor.S_RESIZE_CURSOR:
			        	  if (!(h + dy < 50)) {
				              c.setBounds(x, y, w, h + dy);
				              startPos = me.getPoint();
				              resize();
				              //newY += dy;
				              //rect.getY().setValue(newY);
				              newH +=dy;
				              rect.getHeight().setValue(newH);
				              c.repaint();
				            //  newHeightValue = String.valueOf(rect.getHeight().getValueInSpecifiedUnits())+rect.getHeight().getUserUnit();
				              
				          	
				            }
			            break;

			          case Cursor.W_RESIZE_CURSOR:
			        	  if (!(w - dx < 50)) {
				              c.setBounds(x + dx, y, w - dx, h);
				              resize();
				              newX += dx;
				              rect.getX().setValue(newX);
				              newW -= dx;
				              rect.getWidth().setValue(newW);
				              c.repaint();
				              //newWidthValue = String.valueOf(rect.getWidth().getValueInSpecifiedUnits())+rect.getWidth().getUserUnit();
				              //newXString = String.valueOf(rect.getX().getValueInSpecifiedUnits())+rect.getX().getUserUnit();
				      		
				            }
			            break;

			          case Cursor.E_RESIZE_CURSOR:
			        	  if (!(w + dx < 50)) {
				              c.setBounds(x, y, w + dx, h);
				              startPos = me.getPoint();
				              resize();
				              //newX += dx;
				              //rect.getX().setValue(newX);
				              newW += dx;
				              rect.getWidth().setValue(newW);
				              c.repaint();
				              //newWidthValue = String.valueOf(rect.getWidth().getValueInSpecifiedUnits())+rect.getWidth().getUserUnit();
				            
			        	  }
			            break;

			          case Cursor.NW_RESIZE_CURSOR:
			        	  if (!(w - dx < 50) && !(h - dy < 50)) {
				              c.setBounds(x + dx, y + dy, w - dx, h - dy);
				              resize();
				              newX += dx;
				              rect.getX().setValue(newX);
				              newY += dy;
				              rect.getY().setValue(newY);
				              newW -= dx;
				              rect.getWidth().setValue(newW);
				              newH -= dy;
				              rect.getHeight().setValue(newH);
				              c.repaint();
				              //newWidthValue = String.valueOf(rect.getWidth().getValueInSpecifiedUnits())+rect.getWidth().getUserUnit();
				              //newXString = String.valueOf(rect.getX().getValueInSpecifiedUnits())+rect.getX().getUserUnit();
				              //newHeightValue = String.valueOf(rect.getHeight().getValueInSpecifiedUnits())+rect.getHeight().getUserUnit();
				             // newYString = String.valueOf(rect.getY().getValueInSpecifiedUnits())+rect.getY().getUserUnit();
				          	
				            }
			            break;

			          case Cursor.NE_RESIZE_CURSOR:
			        	  if (!(w + dx < 50) && !(h - dy < 50)) {
				              c.setBounds(x, y + dy, w + dx, h - dy);
				              startPos = new Point(me.getX(), startPos.y);
				              resize();
				              //newX += dx;
				              //rect.getX().setValue(newX);
				              newY += dy;
				              rect.getY().setValue(newY);
				              newW += dx;
				              rect.getWidth().setValue(newW);
				              newH -= dy;
				              rect.getHeight().setValue(newH);
				              c.repaint();
				              //newWidthValue = String.valueOf(rect.getWidth().getValueInSpecifiedUnits())+rect.getWidth().getUserUnit();
				              //newHeightValue = String.valueOf(rect.getHeight().getValueInSpecifiedUnits())+rect.getHeight().getUserUnit();
				             // newYString = String.valueOf(rect.getY().getValueInSpecifiedUnits())+rect.getY().getUserUnit();
				          	  
			        	  }
			            break;

			          case Cursor.SW_RESIZE_CURSOR:
			        	  if (!(w - dx < 50) && !(h + dy < 50)) {
				              c.setBounds(x + dx, y, w - dx, h + dy);
				              startPos = new Point(startPos.x, me.getY());
				              resize();
				              newX += dx;
				              rect.getX().setValue(newX);
				              //newY += dy;
				              //rect.getY().setValue(newY);
				              newW -= dx;
				              rect.getWidth().setValue(newW);
				              newH += dy;
				              rect.getHeight().setValue(newH);
				              c.repaint();
				              //newWidthValue = String.valueOf(rect.getWidth().getValueInSpecifiedUnits())+rect.getWidth().getUserUnit();
				             // newXString = String.valueOf(rect.getX().getValueInSpecifiedUnits())+rect.getX().getUserUnit();
				             // newHeightValue = String.valueOf(rect.getHeight().getValueInSpecifiedUnits())+rect.getHeight().getUserUnit();
				             
				          	  
				            }
			            break;

			         case Cursor.SE_RESIZE_CURSOR:
			        	 if (!(w + dx < 50) && !(h + dy < 50)) {
				              c.setBounds(x, y, w + dx, h + dy);
				              startPos = me.getPoint();
				              resize();
				             // newX += dx;
				             // rect.getX().setValue(newX);
				             // newY += dy;
				             // rect.getY().setValue(newY);
				              newW += dx;
				              rect.getWidth().setValue(newW);
				              newH += dy;
				              rect.getHeight().setValue(newH);
				              c.repaint();
				              //newWidthValue = String.valueOf(rect.getWidth().getValueInSpecifiedUnits())+rect.getWidth().getUserUnit();
				              //newHeightValue = String.valueOf(rect.getHeight().getValueInSpecifiedUnits())+rect.getHeight().getUserUnit();
				              
				          	  
				            }
			          break;

			          case Cursor.MOVE_CURSOR:
			            Rectangle bounds = c.getBounds();
			            bounds.translate(dx, dy);
			            c.setBounds(bounds);
			            resize();
			            
			            newX +=dx;
			            newY +=dy;
			            //System.out.println("newX 1= "+newX);
			            rect.getX().setValue(newX);
			            rect.getY().setValue(newY);
			            
			            //newXString = String.valueOf(rect.getX().getValueInSpecifiedUnits())+rect.getX().getUserUnit();
			            //newYString = String.valueOf(rect.getY().getValueInSpecifiedUnits())+rect.getY().getUserUnit();
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
				            c.getView().repaint(); 
				            
				        } 
					   catch(Exception e){
						   continue;
					   }
				   }
						//System.out.println(iterator.next());
					}

			   public void mouseReleased(MouseEvent mouseEvent) {
				   startPos = null;
				   newXString = String.valueOf(rect.getX().getValueInSpecifiedUnits())+rect.getX().getUserUnit();
		           newYString = String.valueOf(rect.getY().getValueInSpecifiedUnits())+rect.getY().getUserUnit();
		           newWidthValue = String.valueOf(rect.getWidth().getValueInSpecifiedUnits())+rect.getWidth().getUserUnit();
		           newHeightValue = String.valueOf(rect.getHeight().getValueInSpecifiedUnits())+rect.getHeight().getUserUnit();
		              //
				     c.getElement().setAttribute("height", newHeightValue);
				     c.getElement().setAttribute("width", newWidthValue);
				     c.getElement().setAttribute("x", newXString);
				     c.getElement().setAttribute("y", newYString);
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
