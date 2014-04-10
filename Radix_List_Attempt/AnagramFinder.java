// package anagrams;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class AnagramFinder {
	
	public static void main(String[] args) {
		
		if (args.length != 2) { // make sure we run program with correct format
			System.out.println("Must enter file to parse for anagrams and a file to write output to.");
			System.out.println("Format: java <executable> <fileToParse> <fileForOutput>");
			System.exit(0);
		}
		
		File file = new File(args[0]);
		
		try {
			Scanner scan = new Scanner(file);
			
			System.out.println("Creating a new list...");
			ArrayList<ValueString> myList = new ArrayList<ValueString>(); 
			
			System.out.println("Creating a new scanner...");
			scan = new Scanner(file);

			System.out.println("Reading from designated file \'" + file + "\' and assigning keys...");
			int numScanned = 0;
			int mostLetters = 0;
			while (scan.hasNextLine()) { // this will add to the list every string with its determined key
				if (numScanned % 1000000 == 0 && args[0].equals("dict2"))
					System.out.println(numScanned + " words scanned...");
				String scanned = scan.nextLine();
				
				if (scanned.length() > 0) { // don't count any newlines/tabs/etc.
					myList.add(new ValueString(countingSort(scanned), scanned)); // add to the list the key/word pair
					numScanned++;
					if(scanned.length() > mostLetters)
						mostLetters = scanned.length();
				}
			}

			System.out.println("Radix sorting the list based on their keys...");
			radixSort(myList, mostLetters);

			try {
				System.out.println("Placing results in output file...");
				BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));

				int index = 0;
				int stepper = 0;
				int anagramCount = 0;

				while(stepper <= myList.size()) {
					// if stepper stepped off the end of the list, just print the last class of anagrams ()
					if (stepper == myList.size()) {
							for (int i = index; i < stepper; i++) {
								out.write(myList.get(i).getValue() + " ");
							}
							anagramCount++;
						break;
					} else {
						if (stepper < myList.size() && myList.get(index).getKey().equals(myList.get(stepper).getKey()))
							stepper++;
						else {
							for (int i = index; i < stepper; i++)
								out.write(myList.get(i).getValue() + " ");
							out.write("\n");
							anagramCount++;
							index = stepper;
						}
					}
				}
				System.out.println(anagramCount + " anagram classes");
				out.close();
			} catch (IOException e) {}

			System.out.println("Cleaning up...");
			cleanup(myList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All done!");
	}
	
	
	private static void radixSort(ArrayList<ValueString> myList, int mostLetters) {
		
		// have a group of 27 buckets, 0 for null characters, 1-26 for characters a-z
		ArrayList<ArrayList<ValueString>> buckets = new ArrayList<ArrayList<ValueString>>(27);
		for(int i = 0; i < 27; i++) {
			buckets.add(new ArrayList<ValueString>());
		}
		
		/** starting at the most significant digit (in this case, the last character of the longest word)
		for each word, sort every word based on the character at that place. Then clear our list and add back
		in the words from each bucket (must be in ascending order of the buckets)
		*/
		for(int i = mostLetters - 1; i >= 0; i--) {
			for (int j = 0; j < myList.size(); j++) {
				int bucketIndex;
				if (i >= myList.get(j).getKey().length())
					bucketIndex = 0;
				else
					bucketIndex = myList.get(j).getKey().charAt(i) - 96;
				buckets.get(bucketIndex).add(myList.get(j));	
			}
			myList.clear();
			for(ArrayList<ValueString> bucket : buckets) {
				for(ValueString word : bucket) {
					myList.add(word);
				}
				bucket.clear();
			}
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
			characters[input.charAt(j) - 97] = characters[input.charAt(j) - 97] + 1;

		// characters[i] now contains the number of letters that are less 
		// than or equal to the letter in the alphabet that i corresponds to
		for (int i = 1; i < characters.length; i++)  
			characters[i] = characters[i] + characters[i-1];

		// now we do the actual sorting
		for (int i = input.length() - 1; i >= 0; i--) {
			key[characters[input.charAt(i) - 97] - 1] = input.charAt(i);
			characters[input.charAt(i) - 97] = characters[input.charAt(i) - 97] - 1;
		}

		characters = null; // clean up

		return new String(key); // convert the character array to a string and return it
	}
	
	private static void cleanup(ArrayList<ValueString> aList) {
		aList.clear();
		aList = null;
	}
}
