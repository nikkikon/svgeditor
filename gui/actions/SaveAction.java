package svgedit.gui.actions;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.swing.AbstractAction;
import svgedit.gui.Frame;

public class SaveAction extends AbstractAction{

	private Frame frame;
	private File savedFile;
	public SaveAction(Frame frame){
		super("Save");
		this.frame = frame;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String tmpFileName = frame.getTempFile().getName();
		String savedFileName = tmpFileName.substring(0,tmpFileName.indexOf(".tmp"));
		System.out.println(frame.getfilePath());
		if(!frame.getfilePath().equals("DEFAULT")){
			
			savedFile= new File(frame.getfilePath());
			System.out.println(savedFile.getPath());
		}
		else{
			savedFile= new File("./savedFile/"+savedFileName);
			System.out.println(savedFile.getPath());
		}
		
		try {
			fileCopy(frame.getTempFile(),savedFile);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(savedFile.getPath());
		System.out.println("SAVE FILE");
		
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
