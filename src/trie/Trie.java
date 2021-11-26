package trie;

import java.util.LinkedList;
import java.util.Map;

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

    public TrieNode getNode(String str){
        TrieNode current = root;

        for(int i = 0; i < str.length(); i++){
            current = current.getChild(str.charAt(i));

            if(current == null){
                return null;
            }
        }
        return current;
    }

    public TrieNode get(String str){
        TrieNode current = getNode(str);

        if(current != null){
            if(current.isTerminal()){
                return current;
            }
            return null;
        }
        return null;
    }

    public String getMostFrequentWordWithPrefix(String prefix){

        String suffix = "";

        
        return suffix;
    }
}
