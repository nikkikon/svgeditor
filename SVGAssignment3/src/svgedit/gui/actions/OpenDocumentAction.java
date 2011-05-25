package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import svgedit.gui.Frame;
import svgedit.gui.SVGFileFilter;
import svgedit.gui.SVGZFileFilter;

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
        if (frame.confirmSaveChanges()) {
            String directory = frame.getPreferences().getDefaultPath();
            JFileChooser dialog = new JFileChooser(directory);
            dialog.addChoosableFileFilter(new SVGFileFilter());
            dialog.addChoosableFileFilter(new SVGZFileFilter());
            dialog.showOpenDialog(null);

            File file = dialog.getSelectedFile();
            if (file != null) {
                frame.getPreferences().setDefaultPath(file.getPath());
                if(dialog.getFileFilter().getDescription().equals("SVGZ Files")||file.getName().trim().toLowerCase().endsWith(".svgz")){
                	try {
						readSVGZ(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                else{
                frame.openFile(file);
                }
            }
        }
    }
    
    public void readSVGZ(File file) throws IOException{
        frame.isSvgz=true;
        int sChunk = 8192;
    	tmpFile = new File("tmpOpen.svg");
        FileInputStream in = new FileInputStream(file);
        GZIPInputStream zipin = new GZIPInputStream(in);
        byte[] buffer = new byte[sChunk];
        FileOutputStream out = new FileOutputStream(tmpFile);
        int length;
        while ((length = zipin.read(buffer, 0, sChunk)) != -1)
          out.write(buffer, 0, length);
        out.close();
        zipin.close();

    	frame.openFile(tmpFile);
    }

}
