package svgedit.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;




import svgedit.svg.SVGDocument;
import svgedit.svg.SVGRootElement;
import svgedit.svg.SVGViewport;
import svgedit.xml.*;

/** Displays a window containing a SVG view, toolbar and menu. */
@SuppressWarnings("serial")
public class Frame extends JFrame {

    /** User preferences */
    private Preferences preferences;

    /** The document displayed by the frame */
    private SVGDocument document;

    /** An SVG view of the current document */
    private View view;

    // Actions available from the menu and toolbar */
    public ElementView currentElement;
    private SelectMenubar smb;
    private JMenuBar menuBar;
    public EditBox newBox;
    private DocumentPropertyEditBox dpe;
    private File tmpFile;
    private String filePath;
    private Vector<ElementView> jc;
    private SVGRootElement svgRootElement;
    public boolean editBoxExist = false;
    /** Creates a frame with a new, empty document. */
    public Frame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        filePath = "DEFAULT";
        preferences = new Preferences();
        jc = new Vector<ElementView>();
        // Create all actions presented by the menu bar and toolbar
        menuBar = new MainMenuBar(this);
        add(menuBar, BorderLayout.NORTH);
        // Create view
        view = new View(this);
        getContentPane().add(view);
        newFile();
        
        try {
			creatTempFile(new File("./null.svg.tmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        // Set an initial view size in case the document size is based on the view
        view.setSize(new Dimension(480, 360));

        // Size the view to the document
        view.setPreferredSize(new Dimension((int) document.getWidth().getValue(), (int) document.getHeight().getValue()));
        pack();
    }

    /** Gets the preferences object to use for getting and setting user defaults.
     *
     * @return the preferences object associated with this frame
     */
    public Preferences getPreferences() {
        return preferences;
    }

    /** Gets the document currently displayed in the frame.
     *
     * @return an SVG document
     */
    public SVGDocument getDocument() {
        return document;
    }

    /** Sets the document currently displayed in the frame.
     *
     * @param document the document to display
     * @param file the file the document was loaded from, or null if it's a new file
     */
    private void setDocument(SVGDocument document, File file) {
        this.document = document;
        view.setDocument(document);
        setFile(file);
    }

    /** Gets the view presenting the document for this frame. */
    public View getView() {
        return view;
    }

    /**
     * Set the filename of the current document. Sets the frame's title and
     * enables the appropriate save menu items.
     * 
     * @param file
     *            the filename of the current document, or null if the document
     *            is new and has no filename
     */
    private void setFile(File file) {
        document.setFile(file);

        if (file != null)
            setTitle(file.getName() + " - SVGEdit");
        else
            setTitle("SVGEdit");
    }

    /** Sets the document to a new document with no associated file. */
    public final void newFile() {
        SVGDocument doc = new SVGDocument(view);
        setDocument(doc, null);
    }

    /** Loads a file and sets it as the current document.  Displays an error
     *  message and leaves the current document unchanged if the document cannot
     *  be opened.
     *
     * @param file the file to load
     */
    public final void openFile(File file) {
    	clearView();
    	try {
            SVGDocument doc = XMLReader.read(file, view);
            setDocument(doc, file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage(), "Unable to open file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addSelectMenu(){
        smb =new SelectMenubar(this);
    	remove(menuBar);
        add(smb,BorderLayout.NORTH);
    	smb.revalidate();
    	System.out.println("UPDATE");
    	
    }
    
    public void deleteSelectMenu(){
    	menuBar =new MainMenuBar(this);
    	remove(smb);
    	add(menuBar,BorderLayout.NORTH);
    	menuBar.revalidate();
     }
  
    public void startEditBox(){
       addSelectMenu();
    }
  
    
    public void closeEditBox0(){
    	newBox.dispose();
    }
  
    public void startDocumentPropertyEditBox(){
    	dpe = new DocumentPropertyEditBox(this); 
    }  
    
    public void creatTempFile(File tmpFile) throws IOException{
    	this.tmpFile = tmpFile;
    	
    }
    
    public File getTempFile(){
    	return tmpFile;
    }
    
    public String getfilePath(){
    	return filePath;
    }
    
    public void setfilePath(String path){
    	filePath = path;
    }
    public void addJComponent(ElementView c){
    	jc.add(c);
    }
    /** 
     *  get the vector of all the ElementView
     *
     * @return Vector;
     */
    
    public Vector<ElementView> getJCVector(){
    	return jc;
    } 
    public void clearView(){
    	Iterator<ElementView> it = jc.iterator();
    	while(it.hasNext()){
    		view.remove(it.next());
    		System.out.println("REMOVE");
    		
    	}
    	jc.clear();	
    	
    }
    
	public void writeFile() throws IOException{
    	Document doc = XMLReader.getDocument();
	    XMLUtil.write(doc,tmpFile);
	    
    }
	
	public void setNewBox(){
    	newBox = new EditBox(this,currentElement);
    }

	

	
    
}
