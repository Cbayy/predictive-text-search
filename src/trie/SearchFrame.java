package trie;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;

public class SearchFrame extends JFrame implements DocumentListener {

    private JTextArea textArea;
    private String fileName = "data/word-freq.expanded.trim.txt";
    private Trie dictionaryTrie = null;

    private static final String COMMIT_ACTION = "commit";

    private static enum Mode {
        INSERT, COMPLETION
    };

    private Mode mode = Mode.INSERT;

    public SearchFrame(){
        super("Predictive Text");

        textArea.getDocument().addDocumentListener(this);

        InputMap im = textArea.getInputMap();
        ActionMap am = textArea.getActionMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
        am.put(COMMIT_ACTION, new CommitAction());

        dictionaryTrie = Trie.readInDictionary(fileName);
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

    private class CommitAction extends AbstractAction{
        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = textArea.getSelectionEnd();
                textArea.insert(" ", pos);
                textArea.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            } else {
                textArea.replaceSelection("\n");
            }
        }
    }

    public static void main(String args[]){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SearchFrame().setVisible(true);
            }
        });
    }

}
