package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import svgedit.gui.Frame;
import svgedit.gui.SVGFileFilter;

public class SaveAsAction extends AbstractAction{

	private Frame frame;
	public SaveAsAction(Frame frame){
		super("Save As");
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String tmpFileName = frame.getTempFile().getName();
		
		
		String directory = frame.getPreferences().getDefaultPath();
		JFileChooser dialog = new JFileChooser(directory);
        dialog.addChoosableFileFilter(new SVGFileFilter());
        dialog.showSaveDialog(null);
        
        String savePath = dialog.getCurrentDirectory().getPath()+"\\";
        File saveAsFile = null;
        try{
    	   String savedFileName = dialog.getSelectedFile().getName()+".svg";
    	   saveAsFile =  new File(savePath+savedFileName);
       }
        catch (Exception ex){
        	//System.out.println("MUST ENTER A FILE NAME");
        }
        
        
        	
            
            try {
    			fileCopy(frame.getTempFile(),saveAsFile);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			//e1.printStackTrace();
    		}
        
        
        
        System.out.println("SAVE AS");
		
		
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
