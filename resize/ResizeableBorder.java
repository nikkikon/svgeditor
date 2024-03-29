package svgedit.resize;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import svgedit.gui.ElementView;

public class ResizeableBorder implements Border{

	protected int dist;
	protected int locations[];
	protected int cursors[];
	
	public ResizeableBorder(int dist){
		this.dist = dist;
	}

	protected Rectangle getRectangle(int x,int y, int w, int h, int location){
		switch(location){
		case SwingConstants.NORTH:
			return new Rectangle(x + w / 2 - dist / 2, y, dist, dist);
		case SwingConstants.SOUTH:
			return new Rectangle(x + w / 2 - dist / 2, y + h - dist, dist, dist);
		case SwingConstants.EAST:
			return new Rectangle(x + w - dist, y + h / 2 - dist / 2, dist,dist);
		case SwingConstants.WEST:
			return new Rectangle(x, y + h / 2 - dist / 2, dist, dist);
		case SwingConstants.NORTH_EAST:
			return new Rectangle(x + w - dist, y, dist, dist);
		case SwingConstants.NORTH_WEST:
			return new Rectangle(x, y, dist, dist);
		case SwingConstants.SOUTH_EAST:
			return new Rectangle(x + w - dist, y + h - dist, dist, dist);
		case SwingConstants.SOUTH_WEST:
			return new Rectangle(x, y + h - dist, dist, dist);
		}
		return null;
	}
	
	protected int getCursor(MouseEvent me){
		ElementView c = (ElementView) me.getComponent();
		int w = c.getWidth();
		int h = c.getHeight();
		
		for(int i = 0;i< locations.length; i++){
			Rectangle rect  = getRectangle(0,0,w,h,locations[i]);
			
			if(rect.contains(me.getPoint())){
				return cursors[i];
			}
		}
		return Cursor.MOVE_CURSOR;
	}

	public void paintBorder(Component c, Graphics g, int x, int y,
			int w, int h) {
		// TODO Auto-generated method stub
		//g.setColor(Color.WHITE);
		//g.drawRect(x + dist, y + dist, w, h);
		
		if(c.hasFocus()||((ElementView) c).getisIntersect()){
			//System.out.println(((ElementView) c).getisSelected());
			for(int i =0; i<locations.length;i++){
				Rectangle rect = getRectangle(x, y, w, h, locations[i]);
				g.setColor(Color.WHITE);
				g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
		        g.setColor(Color.BLACK);
		        g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
			}
		}
		((ElementView) c).setisIntersect(false);
	}
	

	

	
	
	public Insets getBorderInsets(Component arg0) {
		// TODO Auto-generated method stub
		
		return new Insets(dist,dist,dist,dist);
	}

	@Override
	public boolean isBorderOpaque() {
		// TODO Auto-generated method stub
		return false;
	}


}
