package trie;

public class TrieData {
    private int frequency = -1;

    public TrieData(int frequency){
        this.frequency = frequency;
    }

    public int getFrequency(){
        return frequency;
    }

    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    @Override
    public String toString(){
        return String.valueOf(frequency);
    }
}
