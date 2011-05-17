package svgedit.gui.actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import svgedit.gui.Frame;
import svgedit.gui.PNGFileFilter;
import svgedit.gui.SVGFileFilter;
import svgedit.svg.SVGDocument;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGLength;
import svgedit.svg.SVGPaint;
import svgedit.svg.SVGStylable;

/** Shows a file dialog for the user to select a new file to write the document
 *  to, then saves it at that location.
 */
@SuppressWarnings("serial")
public class SaveDocumentAsAction extends AbstractAction {

    private Frame frame;
    private int pngHeight;
    private int pngWidth;
    private SVGDocument document;

    /** Create this action for the given frame */
    public SaveDocumentAsAction(Frame frame) {
        super("Save As...");
        this.frame = frame;
       
    }

    public void actionPerformed(ActionEvent arg0) {
        File file = frame.getDocument().getFile();
        JFileChooser dialog;
        if (file != null)
            dialog = new JFileChooser(file);
        else
            dialog = new JFileChooser(frame.getPreferences().getDefaultPath());
        dialog.addChoosableFileFilter(new SVGFileFilter());
        dialog.addChoosableFileFilter(new PNGFileFilter());
        dialog.showSaveDialog(null);
        file = dialog.getSelectedFile();
        if (file != null) {
            if (file.exists()) {
                if (JOptionPane.showConfirmDialog(frame.getRootPane(), String.format("Do you want to replace the existing file named %s?", file.getName()), "Overwrite existing file?", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION)
                    return;
            }
           /* try {
				//writePNGEncodeToStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
           // frame.getPreferences().setDefaultPath(file.getPath());
            if(dialog.getFileFilter().getDescription().equals("SVG Files")){
            	frame.saveFile(file);
            }
            else if(dialog.getFileFilter().getDescription().equals("PNG Files")){
            	try {
					writePNGEncodeToStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }
    
    
    public void writePNGEncodeToStream() throws IOException{
    	document = frame.getDocument();
    	//pngHeight = (int) frame.getView().getViewportHeight();
    	//pngWidth = (int) frame.getView().getViewportWidth();
    	pngHeight = (int) document.getHeight().getValue();
    	pngWidth = (int) document.getWidth().getValue();
    	BufferedImage bi = new BufferedImage(pngWidth, pngHeight, BufferedImage.TYPE_INT_ARGB);
    	Graphics ig = bi.createGraphics();
    	//Color c = new Color(0,128,0);
    	ig.setColor(Color.WHITE);
        ig.fillRect(0, 0, pngWidth, pngHeight);
        
        for (SVGElement elem : document) {
        	paintElement(ig,elem);
        }
   // 	ig.drawRect(100, 100, 400, 400);
    	//ig = frame.getGraphics();
    	ImageIO.write(bi, "PNG", new File("D:\\newImage.PNG"));
    }
    
    public void paintElement(Graphics g,SVGElement elem) {

        if (!(elem instanceof SVGStylable))
            return;

        Shape shape = elem.createShape();

        SVGStylable style = (SVGStylable) elem;
        SVGPaint fillPaint = style.getFill();
        SVGPaint strokePaint = style.getStroke();
        SVGLength strokeWidth = style.getStrokeWidth();

        // Fill the interior of the shape
        if (fillPaint.getPaintType() == SVGPaint.SVG_PAINTTYPE_RGBCOLOR) {
        	((Graphics2D) g).setPaint(fillPaint.getRGBColor());
            ((Graphics2D) g).fill(shape);
        	
        }

        // Stroke the outline of the shape
        if (strokePaint.getPaintType() == SVGPaint.SVG_PAINTTYPE_RGBCOLOR) {
            Stroke stroke = new BasicStroke(strokeWidth.getValue());
            ((Graphics2D) g).setStroke(stroke);
            g.setColor(strokePaint.getRGBColor());
            ((Graphics2D) g).draw(shape);
        }
    }

}
