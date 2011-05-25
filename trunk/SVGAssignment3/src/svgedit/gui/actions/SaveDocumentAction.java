package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.swing.AbstractAction;
import svgedit.gui.Frame;

/** Saves the document to the same file it was most recently saved/loaded
 *  from.
 */
@SuppressWarnings("serial")
public class SaveDocumentAction extends AbstractAction {

    private Frame frame;
    private File tmpFile;


    /** Create this action for the given frame */
    public SaveDocumentAction(Frame frame) {
        super("Save");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        // If there's no file yet, show the save as dialog
        if (frame.getDocument().getFile() == null)
            new SaveDocumentAsAction(frame).actionPerformed(ae);
        else
        	if(frame.isSvgz==true){
        		try {
        			System.out.println(frame.getPreferences().getDefaultPath());
					writeSVGZ(frame.getDocument().getFile());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else{
        		frame.saveFile(frame.getDocument().getFile());
        	}
    }

    public void writeSVGZ(File file) throws IOException{
    	//create a tmp file to store the contect to be saved
    	tmpFile = new File("gzipSave.tmp");
    	//save the contect in tmp file
    	frame.saveFile(tmpFile);
    	//read the tmp file then gzip compress it into the target file
    	BufferedReader in = new BufferedReader(new FileReader(tmpFile));
    	BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(frame.getPreferences().getDefaultPath())));
    	int c;
    	while((c=in.read())!=-1){
    		out.write(c);
    	}
    	in.close();
    	out.close();
    	//delete tmp file
    	tmpFile.delete();
    }

}