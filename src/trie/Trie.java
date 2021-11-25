package trie;

public class Trie {

    private TrieNode root = new TrieNode();

    public TrieNode insert(String str, TrieData data){
        TrieNode current = root;

        for(int i = 0; i < str.length(); i++){

            char childChar = str.charAt(i);
            TrieNode child = current.getChild(childChar);

            if(child == null){
                child = new TrieNode();
                current.addChild(str.charAt(i), child);
            }
            current = child;
        }
        current.addData(data);
        current.setTerminal(true);

        return current;
    }
}
