package svgedit.resize;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ResizeableBorderRectangle extends ResizeableBorder implements Border{

	private int dist;
	private int locations[] = {SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.WEST,
		    SwingConstants.EAST, SwingConstants.NORTH_WEST,
		    SwingConstants.NORTH_EAST, SwingConstants.SOUTH_WEST,
		    SwingConstants.SOUTH_EAST};
	private int cursors[] = {
			Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR,
		    Cursor.E_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
		    Cursor.SW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
	};
	public ResizeableBorderRectangle(int dist) {
		super(dist);
		super.locations = this.locations;
		super.cursors = this.cursors;
		this.dist = dist;
	}

	public void print(){
		System.out.println(locations[2]);
	}
	
	

	

}
