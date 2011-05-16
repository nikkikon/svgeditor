package svgedit.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Adapter for DocumentListener that does not distinguish between the type of
 * text change event it receives. Provides comparable TextListener interface to
 * JTextField.
 * 
 * Usage:
 * 
 * <pre>
 * JTextField textField;
 * textField.getDocument().addDocumentListener(new DocumentChangedAdapter() {
 *     public void textChanged() {
 *     }
 * });
 * </pre>
 * 
 */
public abstract class DocumentChangedAdapter implements DocumentListener {

    /** Gives notification that the text in the document was modified. */
    public abstract void documentChanged();

    public void insertUpdate(DocumentEvent de) {
        documentChanged();
    }

    public void removeUpdate(DocumentEvent de) {
        documentChanged();
    }

    public void changedUpdate(DocumentEvent de) {
        documentChanged();
    }
}
