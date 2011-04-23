package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import svgedit.gui.Frame;
import svgedit.gui.SVGFileFilter;

/** Shows discard changes dialog, then presents a file dialog for the
 *  user to select an existing document to edit.
 */
@SuppressWarnings("serial")
public class OpenDocumentAction extends AbstractAction {

    private Frame frame;
    private File tmpFile;
    /** Create this action for the given frame */
    public OpenDocumentAction(Frame frame) {
        super("Open...");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        //String directory = frame.getPreferences().getDefaultPath();
        JFileChooser dialog = new JFileChooser();
        dialog.addChoosableFileFilter(new SVGFileFilter());
        dialog.showOpenDialog(null);

        File file = dialog.getSelectedFile();
        if (file != null) {
            frame.getPreferences().setDefaultPath(file.getPath());
            frame.openFile(file);
            tmpFile = new File("./tmpFile/"+file.getName()+".tmp");
            try {
				fileCopy(file,tmpFile);
				frame.creatTempFile(tmpFile);   
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        }
    }
    
    public void fileCopy(File f1,File f2) throws Exception{
		 int  bytesum  =  0;  
        int  byteread  =  0;  
        File  oldfile  =  f1;
        if  (oldfile.exists())  {  
            InputStream  inStream  =  new  FileInputStream(f1);  
            FileOutputStream  fs  =  new  FileOutputStream(f2);  
            byte[]  buffer  =  new  byte[1444];  
            int  length;  
            while  ((byteread  =  inStream.read(buffer))!=  -1)  {  
                bytesum  +=  byteread;  
                fs.write(buffer,  0,  byteread);  
            }  
            inStream.close();  
        }   
	     }  


}
