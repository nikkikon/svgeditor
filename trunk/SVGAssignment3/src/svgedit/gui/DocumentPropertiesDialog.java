package svgedit.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import svgedit.svg.SVGDocument;
import svgedit.svg.SVGLength;

/** Displays a dialog for the user to view and edit the document
 *  dimensions.
 *
 */
@SuppressWarnings("serial")
public class DocumentPropertiesDialog extends JDialog {

    private SVGDocument document;

    private JTextField widthTextField;
    private JTextField heightTextField;

    private Action okAction;
    private Action cancelAction;
    
    private JComponent jc[] = new JComponent[4];

    /** Creates a dialog for editing the properties of a document.
     *
     * @param document the document to edit
     */
    public DocumentPropertiesDialog(SVGDocument document) {
        this.document = document;

        // Create text fields for modifying the document width and height.
        // Any change to the text fields causes validation, so that the OK
        // button is disabled if invalid input is present in one of the fields.

        widthTextField = new JTextField(document.getWidth().valueAsString());
        widthTextField.getDocument().addDocumentListener(new DocumentChangedAdapter() {
            @Override
            public void documentChanged() {
                validateFields();
            }
        });

        heightTextField = new JTextField(document.getHeight().valueAsString());
        heightTextField.getDocument().addDocumentListener(new DocumentChangedAdapter() {
            @Override
            public void documentChanged() {
                validateFields();
            }
        });

        // Create an OK button that applies the changes to the document when
        // pressed.
        okAction = new AbstractAction("OK") {
            public void actionPerformed(ActionEvent ae) {
                applyChanges();
                setVisible(false);
            }
        };

        // Create a cancel button that simply hides the dialog, returning
        // thread control to the caller.
        cancelAction = new AbstractAction("Cancel") {

            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        };

        // Layout text fields and their labels
        GridBagConstraints c = new GridBagConstraints();
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridBagLayout());

        c.insets = new Insets(4, 8, 4, 8);
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        fieldPanel.add(new JLabel("Width"), c);

        c.gridx = 0;
        c.gridy = 1;
        fieldPanel.add(new JLabel("Height"), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 0;
        fieldPanel.add(widthTextField, c);

        c.gridx = 1;
        c.gridy = 1;
        fieldPanel.add(heightTextField, c);

        add(fieldPanel);

        // OK, Cancel buttons at bottom of dialog
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton(okAction);
        JButton cancelButton = new JButton(cancelAction);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set default and cancel buttons so that enter and escape keys are
        // mapped appropriately.
        getRootPane().setDefaultButton(okButton);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
        getRootPane().getActionMap().put("cancel", cancelAction);

        pack();

        // Increase width for aesthetics, then disable resizing.
        Dimension size = getSize();
        setSize(new Dimension(size.width + 200, size.height));
        setResizable(false);
    }

    /** Checks that the user's input is valid for all fields, and enables or
     *  disables the OK button appropriately.
     */
    private void validateFields() {
        boolean validated = true;
        try {
            SVGLength length = new SVGLength();
            length.setValueFromString(widthTextField.getText());
            length.setValueFromString(heightTextField.getText());
        } catch (NumberFormatException e) {
            validated = false;
        }

        okAction.setEnabled(validated);
    }

    /** Commits changes to the text fields to the document. */
    private void applyChanges() {
        document.getWidth().setValueFromString(widthTextField.getText());
        document.getHeight().setValueFromString(heightTextField.getText());
    }

}
