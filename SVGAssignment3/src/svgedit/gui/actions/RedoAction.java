package svgedit.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import svgedit.gui.Frame;

@SuppressWarnings("serial")
public class RedoAction extends AbstractAction{
    private Frame frame;

    /** Create this action for the given frame */
    public RedoAction(Frame frame) {
        super("Redo");
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae) {
        frame.getView().getCommandStack().redo();
    }

}