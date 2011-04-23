package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import svgedit.gui.Frame;

/** Shows confirm changes dialog, then exits the application.
 */
@SuppressWarnings("serial")
public class QuitAction extends AbstractAction {

    private Frame frame;

    /** Create this action for the given frame */
    public QuitAction(Frame frame) {
        super("Quit");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
    	
    	deleteTempFiles();
    	System.exit(0);
    }
    
    public void deleteTempFiles(){
    	
    	File dir = new File("./tmpFile/");
    	File  filelist[]=dir.listFiles(); 
        
    	int   listlen=filelist.length; 
        for(int   i=0;i <listlen;i++)   { 
          
                filelist[i].delete(); 
            }
    	
    }

}
