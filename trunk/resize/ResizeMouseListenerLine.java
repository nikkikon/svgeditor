package svgedit.resize;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import svgedit.gui.ElementView;
import svgedit.svg.SVGElement;

public class ResizeMouseListenerLine extends ResizeMouseListener implements MouseListenerInterface{

	private ElementView c;
	private MouseInputListener resizeListener;
	
	
	public ResizeMouseListenerLine(ElementView c,ResizeableBorder border){
		this.c = c;
		addResizeMouseListener();
		
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
			              c.setBounds(x, y + dy, w, h - dy);
			              resize();
			            
			            }
			            break;

			          case Cursor.S_RESIZE_CURSOR:
			            if (!(h + dy < 50)) {
			              c.setBounds(x, y, w, h + dy);
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

			          case Cursor.NW_RESIZE_CURSOR:
			            if (!(w - dx < 50) && !(h - dy < 50)) {
			              c.setBounds(x + dx, y + dy, w - dx, h - dy);
			              resize();
			            }
			            break;

			          case Cursor.NE_RESIZE_CURSOR:
			            if (!(w + dx < 50) && !(h - dy < 50)) {
			              c.setBounds(x, y + dy, w + dx, h - dy);
			              startPos = new Point(me.getX(), startPos.y);
			              resize();
			            }
			            break;

			          case Cursor.SW_RESIZE_CURSOR:
			            if (!(w - dx < 50) && !(h + dy < 50)) {
			              c.setBounds(x + dx, y, w - dx, h + dy);
			              startPos = new Point(startPos.x, me.getY());
			              resize();
			            }
			            break;

			          case Cursor.SE_RESIZE_CURSOR:
			            if (!(w + dx < 50) && !(h + dy < 50)) {
			              c.setBounds(x, y, w + dx, h + dy);
			              startPos = me.getPoint();
			              resize();
			            }
			          break;

			          case Cursor.MOVE_CURSOR:
			            Rectangle bounds = c.getBounds();
			            bounds.translate(dx, dy);
			            c.setBounds(bounds);
			            resize();
			          }


			          c.setCursor(Cursor.getPredefinedCursor(cursor));
			        }
			     }

			   public void mouseReleased(MouseEvent mouseEvent) {
			     startPos = null;
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
