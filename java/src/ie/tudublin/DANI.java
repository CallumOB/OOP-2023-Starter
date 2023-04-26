package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;

public class DANI extends PApplet {

	private ArrayList<Word> words = new ArrayList<>();
	private String[] fileArray;
	private int sonnetLines = 14;
	private int wordCount = 8;
	// I found it more logical to use a 2D array for the sonnet.
	// This makes printing it line-by-line easier.
	private String[][] sonnet = new String[sonnetLines][wordCount];

	public void settings() {
		size(1000, 1000);
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
			// First, all punctuation is removed.
			fileArray[i] = fileArray[i].replaceAll("[^\\w\\s]", "");

			// The wordArray string array is assigned each line of fileArray in lowercase, split by spaces.
			wordArray = split(fileArray[i].toLowerCase(), " ");

			for (int j = 0; j < wordArray.length; j++) {
				// If the word in question cannot be found, a new Word instance is added to the array list.
				if (!findWord(wordArray[j])) {
					words.add(new Word(wordArray[j]));
				}

				for (int k = 0; k < words.size(); k++) {
					if (j != wordArray.length - 1) { // This prevents out of bounds exceptions.
						// If the 'words' arraylist contains the word at wordArray[j] then ...
						if (words.get(k).getWord().equals(wordArray[j])) {
							// If the word's 'follows' arraylist contains the word after it, then its count is incremented.
							if (words.get(k).findFollows(wordArray[j + 1])) {
								words.get(k).increaseFollow(wordArray[j + 1]);
							} else {
								// If the word's 'follows' arraylist doesn't contain the word after it, a new Follow instance is created.
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
			// Prints each element using the Word class's toString() method
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
			// Finds a random word with an index between 0 and words.size().
			Word startingWord = words.get((int) random(0, words.size()));
			// The first word of this particular line is preset, based on the random word found.
			sonnet[i][0] = startingWord.getWord();

			// Code runs if the starting word has other words following it.
			if (startingWord.getFollows().size() != 0) {
				// Starting this loop at 1 ensures there are 8 words including the first word.
				for (int j = 1; j <= startingWord.getFollows().size(); j++) {
					// This ensures no more than 8 words are printed, even if the current word has more than 8 following words.
					if (j < wordCount) {
						// A random following word is chosen, with an index between 0 and the size of the word's follows arraylist.
						int randomWord = (int) random(0, startingWord.getFollows().size());
						// The current index of the sonnet 2D array is assigned the random following word found.
						sonnet[i][j] = startingWord.getFollows().get(randomWord).getWord();
					}
				}
			}
		}
	}

	public void printSonnet() {
		for (int i = 0; i < sonnetLines; i++) {
			for (int j = 0; j < wordCount; j++) {
				/* Because of how the createSonnet() method is designed, not all indices of sonnet will be assigned a value.
				 * This if-statement ensures only indices that have an assigned value will be printed. */
				if(sonnet[i][j] != null) {
					print(sonnet[i][j] + " ");
				}
			}

			// Adds a new line after each line of text is printed.
			println();
		}

		println();
	}

	public void displaySonnet() {
		float topBorder = height * 0.25f;
		float textGap = 25;
		
		for (int i = 0; i < sonnetLines; i++) {
			// This string is a temporary variable which stores each line of the sonnet.
			String currentString = new String(); 
			for (int j = 0; j < wordCount; j++) {
				if (sonnet[i][j] != null) { // Explained in the previous method.
					// currentString is assigned each word of the sonnet, along with a space for formatting.
					currentString += sonnet[i][j] + " ";
				}
			}

			// The current line of text is displayed in the middle of the canvas, and textGap is increased each time to move onto the next line.
			text(currentString, width / 2, topBorder + textGap);
			textGap += 25;
		}
	}
}
