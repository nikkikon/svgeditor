package svgedit.gui.manipulators;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import svgedit.gui.View;

/** A manipulator that dispatches events to multiple child manipulators.

 */
public class CompositeManipulator extends Manipulator {

    /**
     * List of manipulators that this manipulator dispatches events to
     */
    private ArrayList<Manipulator> manipulators;

    /** Manipulator that currently has mouse focus */
    private Manipulator mouseManipulator;
    private int mouseButton;

    /** Creates the manipulator for the given view. */
    public CompositeManipulator(View view) {
        super(view);
        manipulators = new ArrayList<Manipulator>();
    }

    /** Gets the list of manipulators within this manipulator */
    public ArrayList<Manipulator> getManipulators() {
        return manipulators;
    }

    @Override
    public void paint(Graphics2D g2d) {
        for (Manipulator manipulator : manipulators) {
            manipulator.paint(g2d);
        }
    }

    @Override
    public boolean mouseClicked(MouseEvent e) {
        for (Manipulator manipulator : manipulators) {
            if (manipulator.mouseClicked(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        // Iterate manipulators in reverse order
        for (int i = manipulators.size() - 1; i >= 0; --i) {
            Manipulator manipulator = manipulators.get(i);
            if (manipulator.mousePressed(e)) {
                mouseManipulator = manipulator;
                mouseButton = e.getButton();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        if (mouseManipulator != null && e.getButton() == mouseButton) {
            mouseManipulator.mouseReleased(e);
            mouseManipulator = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(MouseEvent e) {
        if (mouseManipulator != null) {
            mouseManipulator.mouseDragged(e);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(KeyEvent e) {
        // Iterate manipulators in reverse order
        for (int i = manipulators.size() - 1; i >= 0; --i) {
            Manipulator manipulator = manipulators.get(i);
            if (manipulator.keyPressed(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyReleased(KeyEvent e) {
        // Iterate manipulators in reverse order
        for (int i = manipulators.size() - 1; i >= 0; --i) {
            Manipulator manipulator = manipulators.get(i);
            if (manipulator.keyReleased(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(KeyEvent e) {
        // Iterate manipulators in reverse order
        for (int i = manipulators.size() - 1; i >= 0; --i) {
            Manipulator manipulator = manipulators.get(i);
            if (manipulator.keyTyped(e)) {
                return true;
            }
        }
        return false;
    }

    /** Gets the cursor of the top-most manipulator.
     *
     * @param point the current mouse position
     * @return the mouse cursor of the top-most manipulator
     */
    @Override
    public Cursor getCursor(Point point) {
        return manipulators.get(manipulators.size() - 1).getCursor(point);
    }

}
