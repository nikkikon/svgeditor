package svgedit;

import java.io.File;

import svgedit.gui.Frame;
import svgedit.resize.ResizeableBorderCircle;
import svgedit.resize.ResizeableBorderRectangle;

/** Main program state and program entry point */
public class Main {

    /** Program entry point.  An optional argument will be interpreted as
     *  a file to load on startup. */
    public static void main(String[] args) throws java.io.IOException {
        Frame frame = new Frame();
        
        if (args.length > 0)
            frame.openFile(new File(args[0]));
        frame.setVisible(true);
    }

};