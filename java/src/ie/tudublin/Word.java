package ie.tudublin;

import java.util.ArrayList;

public class Word {
    private String word;
    private ArrayList<Follow> follows = new ArrayList<>();

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Follow> getFollows() {
        return follows;
    }

    public void setFollows(ArrayList<Follow> follows) {
        this.follows = follows;
    }

    @Override
    public String toString() {
        String returnString = new String();

        returnString += word + ": ";
        for (int i = 0; i < follows.size(); i++) {
            returnString += follows.get(i).getWord() + "(" + follows.get(i).getWordCount() + ") ";
        }

        return returnString;
    }

    public boolean findFollows(String follow) {
        for (int i = 0; i < follows.size(); i++) {
            if (follows.get(i).getWord().equals(follow)) {
                return true;
            }
        }

        return false;
    }

    public void increaseFollow(String follow) {
        for (int i = 0; i < follows.size(); i++) {
            if (follows.get(i).getWord().equals(follow)) {
                follows.get(i).increaseCount();
            }
        }
    }
}
