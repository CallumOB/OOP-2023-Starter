package ie.tudublin;

public class Follow {
    private String word;
    private int wordCount = 0;

    public Follow(String word) {
        this.word = word;
        increaseCount();
    }

    // Increases the amount of times the word has been found. Useful for calling from other methods.
    public void increaseCount() {
        wordCount++;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }  
}
