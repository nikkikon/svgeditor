package svgedit.gui.actions;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import svgedit.gui.Frame;

@SuppressWarnings("serial")
public class JapaneseAction extends AbstractAction{
	private Frame frame;
	private ResourceBundle rb;
	
	public JapaneseAction(Frame frame) {
        super("Japanese");
        this.frame = frame;
    }
	
	public void changeProperties(){
		frame.restoreAllMenu();
		frame.rb = ResourceBundle.getBundle( 
                "jp", 
                frame.getLocales()[2]);
    	
    	for( int i = 0; i < frame.getJMenus().length; i++ )
        {
    	//System.out.println( frame.getJMenus()[i].getText().trim());
    	String str = frame.getJMenus()[i].getText().trim().replaceAll(" ", "");
    		frame.getJMenus()[i].setText( frame.rb.getString( str) );
        }
    	
    	for( int i = 0; i < frame.getJMenuItems().length; i++ )
        {
    	System.out.println( frame.getJMenuItems()[i].getText().trim());
    	String str = frame.getJMenuItems()[i].getText().trim().replaceAll(" ", "");
    	frame.getJMenuItems()[i].setText( frame.rb.getString( str) );
        }
    	
    	for( int i = 0; i < frame.getJLabel().length; i++ )
        {
    	System.out.println( frame.getJLabel()[i].getText().trim());
    	String str = frame.getJLabel()[i].getText().trim().replaceAll(" ", "");
    	str = str.replaceAll(":", "");
    	frame.getJLabel()[i].setText( frame.rb.getString( str ) );
        }
    	for( int i = 0; i < frame.getJToggleButton().length; i++ )
        {
    	System.out.println( frame.getJToggleButton()[i].getText().trim());
    	String str = frame.getJToggleButton()[i].getText().trim().replaceAll(" ", "");
    	str = str.replaceAll(":", "");
    	frame.getJToggleButton()[i].setText( frame.rb.getString( str ) );
        }
    	frame.setLanguage("jp");
    	frame.repaint();
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		changeProperties();
	}

}

