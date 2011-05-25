package svgedit.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import svgedit.gui.Frame;
import svgedit.svg.SVGElement;

@SuppressWarnings("serial")
public class UndoAction extends AbstractAction{
    private Frame frame;

    /** Create this action for the given frame */
    public UndoAction(Frame frame) {
        super("Undo");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        frame.getView().getCommandStack().undo();
        frame.getView().repaint();
    }

}