package trie;

import java.util.Map;
import java.util.TreeMap;

public class TrieNode {

    private TrieData data = null;

    //If this node the terminating character for any word
    private boolean terminal = false;

    //The number of children this node has
    private int numChildren = 0;

    //Map of this node's children
    Map<Character, TrieNode> children = new TreeMap<>();

    public TrieNode getChild(char label){
        return children.get(label);
    }

    public void addChild(char label, TrieNode node){
        children.put(label, node);
    }

    public void addData(TrieData data){
        this.data = data;
    }

    public TrieData getData(){
        return data;
    }

    public boolean isTerminal(){
        return terminal;
    }

    public void setTerminal(boolean terminal){
        this.terminal = terminal;
    }

    @Override
    public String toString(){
        return "TrieNode; isTerminal=" +terminal+ ", data="+this.data+", #children="+children.size();
    }
}
