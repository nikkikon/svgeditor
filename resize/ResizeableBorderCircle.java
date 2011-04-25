package svgedit.resize;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ResizeableBorderCircle extends ResizeableBorder implements Border{

	
	private int locations[] = {
			SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.WEST,
		    SwingConstants.EAST 
		    
	};
	
	private int cursors[] = {
			Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR,
		    Cursor.E_RESIZE_CURSOR
			
	};
	public ResizeableBorderCircle(int dist) {
		super(dist);
		super.locations = this.locations;
		super.cursors = this.cursors;
		this.dist = dist;
	}

	

}
