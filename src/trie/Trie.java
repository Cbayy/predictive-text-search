package trie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

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
        TrieNode start = getNode(prefix);

        if(start == null){
            return null;
        }

        String suffix = "";
        LinkedList<TrieNode> allChildren = new LinkedList<>();
        int mostFreq = 0;
        Character next = null;

        //Loops through all the children in the current node (start) and finds the highest frequency child
        for(Map.Entry<Character, TrieNode> child: start.children.entrySet()){
            allChildren = findAll(child.getValue(), prefix, allChildren);
            TrieNode startNode;
            for(TrieNode t: allChildren){
                if(t.getData().getFrequency() > mostFreq) {
                    mostFreq = t.getData().getFrequency();
                    startNode = t;
                    next = child.getKey();
                }
            }

        }

        //Appends the result to the empty/current string, recursively calls itself
        suffix = String.valueOf(next);
        suffix = getMostFrequentWordWithPrefix(prefix + suffix);

        //If the found suffix has a lower frequency than the current prefix, returns prefix
        if(start.isTerminal() && start.getData().getFrequency() > mostFreq){
            return prefix;
        }
        return suffix;
    }


    public LinkedList<TrieNode> findAll(TrieNode curr, String prefix, LinkedList<TrieNode> allChildren){
        for(Map.Entry<Character, TrieNode> set: curr.children.entrySet()){
            findAll(set.getValue(), prefix.concat(String.valueOf(set.getKey())), allChildren);

        }
        if(curr.isTerminal()){
            allChildren.add(curr);
        }
        return allChildren;

    }

}
