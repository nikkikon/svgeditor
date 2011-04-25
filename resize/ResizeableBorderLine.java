package svgedit.resize;

import java.awt.Cursor;

import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ResizeableBorderLine extends ResizeableBorder implements Border{

	
	private int locations[] = {
			SwingConstants.NORTH_WEST, SwingConstants.SOUTH_EAST
		    
			
	};
	private int cursors[] = {
			
			Cursor.NW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
	};
	
	public ResizeableBorderLine(int dist) {
		super(dist);
		super.locations = this.locations;
		super.cursors = this.cursors;
		this.dist = dist;
	}

}
