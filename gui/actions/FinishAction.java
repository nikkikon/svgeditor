package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import svgedit.gui.Frame;
import svgedit.gui.NewFile;

public class FinishAction extends AbstractAction{

	private NewFile newFile;
	private Frame frame;
	private String newFileName;
	private File tmpFile;
	private File nullFile;
	public FinishAction(NewFile newFile,Frame frame){
		super("Finish");
		this.newFile = newFile;
		this.frame = frame;

	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		newFileName = newFile.getNewFileName();
		if(newFileName.equals("")){
			JOptionPane.showMessageDialog(newFile,"Must Enter a File Name");
		}
		else{
			tmpFile = new File("./tmpFile/"+newFileName+".svg.tmp");
			try {
				nullFile = new File("null.svg.tmp");
			    fileCopy(nullFile,tmpFile);
			    frame.creatTempFile(tmpFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("CREAT TMP FILE,FILE NAME = "+frame.getTempFile().getName());
		    frame.openFile(tmpFile);
		    
		    newFile.dispose();
			
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


