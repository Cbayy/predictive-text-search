package trie;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;

public class SearchFrame extends JFrame implements DocumentListener {

    private JTextArea textArea;
    private String fileName = "data/word-freq.expanded.trim.txt";
    private Trie dictionaryTrie = null;
    private JScrollPane jScrollPane1;

    private static final String COMMIT_ACTION = "commit";

    private static enum Mode {
        INSERT, COMPLETION
    };

    private Mode mode = Mode.INSERT;

    public SearchFrame(){
        super("Predictive Text");
        initComponents();

        textArea.getDocument().addDocumentListener(this);

        InputMap im = textArea.getInputMap();
        ActionMap am = textArea.getActionMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
        am.put(COMMIT_ACTION, new CommitAction());

        dictionaryTrie = Trie.readInDictionary(fileName);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if(e.getLength() !=1){
            return;
        }
        int pos = e.getOffset();
        String content = null;

        try {
            content = textArea.getText(0, pos+1);
        } catch (BadLocationException badLocationException) {
            badLocationException.printStackTrace();
        }

        int w;
        for (w = pos; w >= 0; w--) {
            if (!Character.isLetter(content.charAt(w))) {
                break;
            }
        }
        //Added check if incoming prefix is smaller than 1 letter
        if (pos - w < 1) {
            return;
        }

        String prefix = content.substring(w+1).toLowerCase();
        String suffix = dictionaryTrie.getMostFrequentWordWithPrefix(prefix);

        if(suffix != null){
            if (!suffix.equals(prefix) && suffix.startsWith(prefix)) {
                String completion = suffix.substring(pos-w);
                SwingUtilities.invokeLater(new CompletionTask(completion, pos+1));
            }
        }else{
            mode = Mode.INSERT;
        }

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    private class CompletionTask implements Runnable{
        String completion;
        int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        @Override
        public void run() {
            textArea.insert(completion, position);
            textArea.setCaretPosition(position + completion.length());
            textArea.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
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

    private void initComponents() {
        textArea = new JTextArea();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);

        jScrollPane1 = new JScrollPane(textArea);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        //Create a parallel group for the horizontal axis
        GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        //Create a sequential and a parallel groups
        GroupLayout.SequentialGroup h1 = layout.createSequentialGroup();
        GroupLayout.ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        //Add a scroll panel and a label to the parallel group h2
        h2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        //h2.addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);

        //Add a container gap to the sequential group h1
        h1.addContainerGap();
        // Add the group h2 to the group h1
        h1.addGroup(h2);
        h1.addContainerGap();
        //Add the group h1 to hGroup
        hGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);
        //Create the horizontal group
        layout.setHorizontalGroup(hGroup);

        //Create a parallel group for the vertical axis
        GroupLayout.ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        //Create a sequential group
        GroupLayout.SequentialGroup v1 = layout.createSequentialGroup();
        //Add a container gap to the sequential group v1
        v1.addContainerGap();
        //Add a label to the sequential group v1
        //v1.addComponent(jLabel1);
        v1.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        //Add scroll panel to the sequential group v1
        v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
        v1.addContainerGap();
        //Add the group v1 to vGroup
        vGroup.addGroup(v1);
        //Create the vertical group
        layout.setVerticalGroup(vGroup);
        pack();
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
