package svgedit.resize;

import java.awt.Cursor;

import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ResizeableBorderLine extends ResizeableBorder implements Border{

	
	private int locations[] = {SwingConstants.NORTH_WEST,SwingConstants.SOUTH_EAST};
	private int cursors[] = {
			
			Cursor.NW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
	};
	
	public ResizeableBorderLine(int dist,int LineType) {
		super(dist);
		System.out.println("Line Type "+LineType);
		if(LineType==2){
			locations[0] = SwingConstants.NORTH_EAST;
			locations[1] = SwingConstants.SOUTH_WEST;
			cursors[0] = Cursor.NE_RESIZE_CURSOR;
			cursors[1] = Cursor.SW_RESIZE_CURSOR;
		}
		super.locations = this.locations;
		super.cursors = this.cursors;
		this.dist = dist;
	}

}
