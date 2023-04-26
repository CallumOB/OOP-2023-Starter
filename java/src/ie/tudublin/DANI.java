package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;

public class DANI extends PApplet {

	private ArrayList<Word> words = new ArrayList<>();
	private String[] fileArray;

	public void settings() {
		size(1000, 1000);
	}

	String[] sonnet;

	public String[] writeSonnet() {
		return null;
	}

	public void setup() {
		colorMode(HSB);
		loadFile();
		printModel();
	}

	public void keyPressed() {
		if (key == ' ') {
			createSonnet();
		}
	}

	float off = 0;

	public void draw() {
		background(0);
		fill(255);
		noStroke();
		textSize(20);
		textAlign(CENTER, CENTER);

	}

	public void loadFile() {
		String[] wordArray;
		fileArray = loadStrings("java/data/small.txt");

		for (int i = 0; i < fileArray.length; i++) {
			fileArray[i].replaceAll("[^\\w\\s]", "");

			wordArray = split(fileArray[i].toLowerCase(), " ");

			for (int j = 0; j < wordArray.length; j++) {
				if (findWord(wordArray[j]) == false) {
					words.add(new Word(wordArray[j]));
				}

				for (int k = 0; k < words.size(); k++) {
					if (j != wordArray.length - 1) {
						if (words.get(k).getWord().equals(wordArray[j])) {
							if (words.get(k).findFollows(wordArray[j + 1])) {
								words.get(k).increaseFollow(wordArray[j + 1]);
							} else {
								words.get(k).getFollows().add(new Follow(wordArray[j + 1]));
							}
						}
					}
				}
			}
		}
	}

	public void printModel() {
		for (int i = 0; i < words.size(); i++) {
			println(words.get(i));
		}
	}

	public boolean findWord(String word) {
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).getWord().equals(word)) {
				return true;
			}
		}

		return false;
	}

	public void createSonnet() {
		int sonnetLines = 14;
		int wordCount = 8;
		String[][] sonnet = new String[sonnetLines][wordCount];

		for (int i = 0; i < sonnetLines; i++) {
			Word startingWord = words.get((int) random(0, words.size()));
			sonnet[i][0] = startingWord.getWord();

			if (startingWord.getFollows().size() != 0) {
				for (int j = 1; j < startingWord.getFollows().size(); j++) {
					if (j < wordCount) {
						int randomWord = (int) random(0, startingWord.getFollows().size());
						sonnet[i][j] = startingWord.getFollows().get(randomWord).getWord();
					}
				}
			}
		}
	}
}
