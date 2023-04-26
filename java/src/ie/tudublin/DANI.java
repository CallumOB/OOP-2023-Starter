package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;

public class DANI extends PApplet {

	private ArrayList<Word> words = new ArrayList<>();
	private String[] fileArray;
	private int sonnetLines = 14;
	private int wordCount = 8;
	private String[][] sonnet = new String[sonnetLines][wordCount];

	public void settings() {
		size(1000, 1000);
	}

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
			printSonnet();
		}
	}

	float off = 0;

	public void draw() {
		background(0);
		fill(255);
		noStroke();
		textSize(20);
		textAlign(CENTER, CENTER);

		displaySonnet();
	}

	public void loadFile() {
		String[] wordArray;
		fileArray = loadStrings("java/data/shakespere.txt");

		for (int i = 0; i < fileArray.length; i++) {
			fileArray[i] = fileArray[i].replaceAll("[^\\w\\s]", "");

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

		println();
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
		for (int i = 0; i < sonnetLines; i++) {
			Word startingWord = words.get((int) random(0, words.size()));
			sonnet[i][0] = startingWord.getWord();

			if (startingWord.getFollows().size() != 0) {
				// Starting this loop at 1 ensures there are 8 words including the first word
				for (int j = 1; j <= startingWord.getFollows().size(); j++) {
					if (j < wordCount) {
						int randomWord = (int) random(0, startingWord.getFollows().size());
						sonnet[i][j] = startingWord.getFollows().get(randomWord).getWord();
					}
				}
			}
		}
	}

	public void printSonnet() {
		for (int i = 0; i < sonnetLines; i++) {
			for (int j = 0; j < wordCount; j++) {
				if(sonnet[i][j] != null) {
					print(sonnet[i][j] + " ");
				}
			}

			println();
		}

		println();
	}

	public void displaySonnet() {
		float topBorder = height * 0.25f;
		float textGap = 25;
		
		for (int i = 0; i < sonnetLines; i++) {
			String currentString = new String();
			for (int j = 0; j < wordCount; j++) {
				if (sonnet[i][j] != null) {
					currentString += sonnet[i][j] + " ";
				}
			}

			text(currentString, width / 2, topBorder + textGap);
			textGap += 25;
		}
	}
}
