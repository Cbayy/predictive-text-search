package trie;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SearchFrame extends JFrame implements DocumentListener {

    private JTextArea textArea;

    private static final String COMMIT_ACTION = "commit";

    private static enum Mode {
        INSERT, COMPLETION
    };

    public SearchFrame(){
        super("Predictive Text");

        textArea.getDocument().addDocumentListener(this);

        InputMap im = textArea.getInputMap();
        ActionMap am = textArea.getActionMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }


}
