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
	}

	public void keyPressed() {
		if (key == ' ') {
			// do something
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
			fileArray[i].toLowerCase();

			wordArray = split(fileArray[i], " ");

			for (int j = 0; j < wordArray.length; j++) {
				if (!findWord(wordArray[j])) {
					words.add(new Word(wordArray[j]));
				}

				if (i != words.size() - 1) {
					if (words.get(i).getWord() == wordArray[j]) {
						if (!words.get(i).findFollows(wordArray[j + 1])) {
							words.get(i).getFollows().add(new Follow(wordArray[j + 1]));
						} else {
							words.get(i).increaseFollow(wordArray[j + 1]);
						}
					}
				}
			}
		}

	}

	public boolean findWord(String word) {
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).getWord() == word) {
				return true;
			}
		}

		return false;
	}
}
