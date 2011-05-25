package svgedit.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import svgedit.svg.SVGPaint;

/** Drop-down widget for editing a paint property.
 *
 *  The drop-down consists of a color picker, and a button for removing
 *  the paint (setting its value to {@literal null}.
 *
 *  The style of the drop-down determines whether it is displayed in the
 *  widget as a filled or stroked rectangle.
 */
@SuppressWarnings("serial")
public class PaintDropDown extends JButton {

    private static final int ICON_MARGIN = 2;
    private static final int ICON_WIDTH = 28;
    private static final int ICON_HEIGHT = 12;

    /** Style value indicating the paint property refers to an element's
     *  fill attribute.
     */
    public static final int PAINT_ATTRIBUTE_FILL = 0;

    /** Style value indicating the paint property refers to an element's
     *  stroke attribute.
     */
    public static final int PAINT_ATTRIBUTE_STROKE = 1;

    private int attribute;
    private SVGPaint paint;
    @SuppressWarnings("unused")
	private Frame frame;

    private JColorChooser colorChooser;
    private JToggleButton removeColorButton;
    private JPopupMenu menu;

    /** Creates a paint drop-down.
     *
     * @param attribute either {@link #PAINT_ATTRIBUTE_FILL} or {@link #PAINT_ATTRIBUTE_STROKE},
     *   giving the attribute this drop-down is editing.
     */
    public PaintDropDown(int attribute,Frame frame) {
        this.attribute = attribute;
        this.paint = new SVGPaint();
        this.frame=frame;

        addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
                showDropDown();
            }
        });

        String removeColorString="No Fill";
        Locale locale =null;
        if(frame.getLanguage().equals("en")){
        	locale = frame.getLocales()[0];
        }
        else if(frame.getLanguage().equals("ge")){
        	locale = frame.getLocales()[1];
        }
        else if(frame.getLanguage().equals("jp")){
        	locale = frame.getLocales()[2];
        }
      
        	frame.rb = ResourceBundle.getBundle( 
                    "LOCALE_"+frame.getLanguage(), 
                    locale);
            if (attribute == PAINT_ATTRIBUTE_FILL){
        	 
             	
             	String str1 = "NoFill";
                removeColorString = frame.rb.getString(str1);
            }
            else{
            	String str2 = "NoStroke";
                removeColorString = frame.rb.getString(str2);
            }
            


        removeColorButton = new JToggleButton(removeColorString);
        removeColorButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                if (removeColorButton.isSelected()) {
                    paint.setRGBColor(null);
                    updateIcon();
                    firePaintChanged();
                }
                menu.setVisible(false);
            }
        });

        colorChooser = new JColorChooser();
        colorChooser.setPreviewPanel(new JPanel());        
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent ce) {
            	
                
               paint.setRGBColor(colorChooser.getColor());
               updateIcon();
               firePaintChanged();
            }
        });  
        menu = new JPopupMenu();
        menu.add(removeColorButton);
        menu.add(colorChooser);

        updateIcon();
        //frame.setDropDown();
    }

    /** Gets the current paint value shown by the drop-down.
     *
     * @return a new {@link SVGPaint} value.
     */
    public SVGPaint getPaint() {
        return paint;
    }
    
    public JColorChooser getColorChooser(){
    	return colorChooser; 
    }

    // Interface for ItemSelectable
    @Override
    public Object[] getSelectedObjects() {
        return new Object[] { paint };
    }
    
    public int getAttribute(){
    	return attribute;
    }

    /** Sets the current paint value displayed by the drop-down.
     *
     * @param paint the paint value to display
     */
    public void setPaint(SVGPaint paint) {
        if (paint != null) {
            this.paint.setValueFromPaint(paint);
            colorChooser.setColor(paint.getRGBColor());
        } else {
            this.paint.setRGBColor(null);
            colorChooser.setColor(null);
        }
        updateIcon();
    }

    public void updateIcon() {
        setIcon(createIcon(paint, attribute));
    }

    /** Causes the drop-down to display */
    public void showDropDown() {
        Rectangle bounds = getBounds();
        removeColorButton.setSelected(paint == null);
        menu.show(this, 0, bounds.height);
    }

    /** Invokes the {@link ItemEvent#ITEM_STATE_CHANGED} event on all listeners */
    public void firePaintChanged() {
        fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, paint, ItemEvent.SELECTED));
    }

    /** Constructs an {@link Icon} to display the current paint. */
    private static Icon createIcon(final SVGPaint paint, final int style) {
        return new Icon() {

            public void paintIcon(Component cmpnt, Graphics g, int x, int y) {
                if (paint.getPaintType() == SVGPaint.SVG_PAINTTYPE_NONE)
                    return;

                x += ICON_MARGIN;
                y += ICON_MARGIN;
                g.setColor(paint.getRGBColor());
                if (style == PAINT_ATTRIBUTE_FILL)
                    g.fillRect(x, y, ICON_WIDTH, ICON_HEIGHT);
                else
                    g.drawRect(x, y, ICON_WIDTH, ICON_HEIGHT);
            }

            public int getIconWidth() {
                return ICON_WIDTH + ICON_MARGIN * 2;
            }

            public int getIconHeight() {
                return ICON_HEIGHT + ICON_MARGIN * 2;
            }
        };
    }
}
