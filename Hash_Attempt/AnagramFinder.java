//package anagrams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class AnagramFinder {
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Must enter file to parse for anagrams.");
			System.out.println("Format: java <executable> <fileToParse>");
			System.exit(0);
		}
		
		File file = new File(args[0]);
		//File file = new File("dict1");

		
		try {
			Scanner scan = new Scanner(file);
			/*while (scan.hasNextLine()) {
				String currWord = scan.nextLine();
				if (currWord.length() > 0) {
					countingSort(currWord);
				}
			}*/

			scan = new Scanner(file);

			int wordCount = 0;

			while (scan.hasNextLine()) { // ** DICT2 TIME ON MY MAC: real 	5.838s **
				String scanned = scan.nextLine();
				if (scanned.length() > 0)  // if a word has at least one letter
					wordCount++; // see how many words there are	
			}
			MyHashTable hashTable = new MyHashTable(wordCount/10); // approximate of how many anagram classes there are
			
			scan = new Scanner(file);

			while (scan.hasNextLine()) {
				String scanned = scan.nextLine();
				if (scanned.length() > 0) 
					hashTable.insert(new ValueString(countingSort(scanned), scanned));
					
			}
			hashTable.print();
			/*for (int i = 0; i < words.length; i++) {
				System.out.println(words[i]);
			}*/
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Counting sort, modeled after that of page 195 of the text.
	 * @param input: a String that is to be sorted
	 * @return the sorted string that is to act as a key for the input
	 */
	private static String countingSort(String input) {
		
		int characters[] = new int[26]; // this acts as the C array from the book's counting sort
		// this assumes we're using the 26-character (a - z) English alphabet, which works fine for the input given in both
		// scenarios
		
		char key[] = new char[input.length()]; // this acts as the B array from the book

		// characters[i] now contains the number of letters from input 
		// that correspond to their position in the alphabet
		for (int j = 0; j < input.length(); j++) 
			characters[charIndex(input.charAt(j))] = characters[charIndex(input.charAt(j))] + 1;

		// characters[i] now contains the number of letters that are less 
		// than or equal to the letter in the alphabet that i corresponds to
		for (int i = 1; i < characters.length; i++)  
			characters[i] = characters[i] + characters[i-1];

		// now we do the actual sorting
		for (int i = input.length() - 1; i >= 0; i--) {
			key[characters[charIndex(input.charAt(i))] - 1] = input.charAt(i);
			characters[charIndex(input.charAt(i))] = characters[charIndex(input.charAt(i))] - 1;
		}

		//System.out.println(input + "\'s key is " + s);

		characters = null; // clean up

		return new String(key); // convert the character array to a string and return it
	}

	private static int charIndex (char character) {
		return ((character - 19) % 26);
	}

	/*private static void printCharArray() {
		for (int i = 0; i < 26; i++) {
			System.out.print("[" + characters[i] + "]");
		}
		System.out.println();
	}*/


}
