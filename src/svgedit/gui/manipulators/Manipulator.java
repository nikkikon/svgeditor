package svgedit.gui.manipulators;

import java.awt.*;
import java.awt.event.*;
import svgedit.gui.View;

/** An abstract aspect of the view that can receive events and paint itself
 *  in the view.
 *
 *  Manipulators are used to specify the functionality of the view.  Rather
 *  than write complicated event handlers within the view itself to handle the
 *  various aspects of selection, manipulation, and drawing new elements, these
 *  functions are split into separate manipulator classes which can be instantiated
 *  and attached to the view at will.
 */
public abstract class Manipulator {
    private View view;

    /** Construct a manipulator on the given view.
     *
     * @param view the view the manipulator belongs to
     */
    public Manipulator(View view) {
        this.view = view;
    }

    /** Gets the view that the manipulator belongs to.
     *
     * @return the view that the manipulator belongs to
     */
    public View getView() {
        return view;
    }

    /** Optional method to paint the manipulator in the view.  Manipulators
     *  are given a chance to paint themselves after all elements are drawn.
     *
     * @param g2d the graphics context to paint into
     */
    public void paint(Graphics2D g2d) {
    }

    /** Optional method to implement to receive a mouse clicked event
     *
     * @param e the mouse event arguments
     * @return {@literal true} if the method was implemented and should prevent
     *   the event from propagating to other manipulators.
     */
    public boolean mouseClicked(MouseEvent e) {
        return false;
    }

    /** Optional method to implement to receive a mouse pressed event
     *
     * @param e the mouse event arguments
     * @return {@literal true} if the method was implemented and should prevent
     *   the event from propagating to other manipulators.
     */
    public boolean mousePressed(MouseEvent e) {
        return false;
    }

    /** Optional method to implement to receive a mouse released event
     *
     * @param e the mouse event arguments
     * @return {@literal true} if the method was implemented and should prevent
     *   the event from propagating to other manipulators.
     */
    public boolean mouseReleased(MouseEvent e) {
        return false;
    }

    /** Optional method to implement to receive a mouse dragged event
     *
     * @param e the mouse event arguments
     * @return {@literal true} if the method was implemented and should prevent
     *   the event from propagating to other manipulators.
     */
    public boolean mouseDragged(MouseEvent e) {
        return false;
    }

    /** Optional method to implement to receive a key pressed event
     *
     * @param e the key event arguments
     * @return {@literal true} if the method was implemented and should prevent
     *   the event from propagating to other manipulators.
     */
    public boolean keyPressed(KeyEvent e) {
        return false;
    }

    /** Optional method to implement to receive a key released event
     *
     * @param e the key event arguments
     * @return {@literal true} if the method was implemented and should prevent
     *   the event from propagating to other manipulators.
     */
    public boolean keyReleased(KeyEvent e) {
        return false;
    }

    /** Optional method to implement to receive a key typed event
     *
     * @param e the key event arguments
     * @return {@literal true} if the method was implemented and should prevent
     *   the event from propagating to other manipulators.
     */
    public boolean keyTyped(KeyEvent e) {
        return false;
    }

    /** Optional method to implement to modify the mouse cursor.
     *
     * @param point the current position of the mouse
     * @return the mouse cursor to set
     */
    public Cursor getCursor(Point point) {
        return Cursor.getDefaultCursor();
    }

}
