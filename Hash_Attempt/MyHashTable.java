//package anagrams;

public class MyHashTable {

	private int length;
	private int occupied;
	private ValueString hashTable[];

	public MyHashTable (int length) {
		this.length = length;
		this.occupied = 0;
		this.hashTable = new ValueString[length];
	}

	public void insert (ValueString valueString) {
		int hashValue = getHash(valueString.getKey());

		if (occupied % (100) == 0)
			System.out.println(occupied + "/" + length);

		if (occupied % 200000 == 0)
			print();

		if (occupied == length) {
			// TODO: extend hash table
			//System.out.println("ERROR: TABLE FULL");
			//this.extendTable();
		}

		else if (hashTable[hashValue] == null) { // if we hash to an empty slot
			hashTable[hashValue] = valueString;
			occupied++;
		} 
		else if (hashTable[hashValue].getKey().equals(valueString.getKey())) { // if the slot is occupied and the keys match
			valueString.setNext(hashTable[hashValue]); // make valueString's next reference point to the first value in the list
			hashTable[hashValue] = valueString; // reset the front of the list to point to valueString (and thus the rest of the list)
		}
		else { // if the slot is occupied and the keys DON'T match
			int timesProbed = 0;
			while(hashTable[hashValue] != null) {
				//System.out.println("rehashing key: " + valueString.getKey() + ", occupancy = " + occupied + "/" + length);
				hashValue = rehashCollision(hashValue, timesProbed);
				timesProbed++;
			}
			hashTable[hashValue] = valueString;
			occupied++;
		}
		
	}

	/**
	*	hash function: 
	*	@param input: a ValueStringKey
	*	@return int: a hash value
	*/
	private int getHash (String valueStringKey) {
		int k = 0;
		for (int i = 0; i < valueStringKey.length(); i++) 
			k += (this.length-1)*valueStringKey.charAt(i);
		k *= (this.length-1);
		if (k < 0)
			k *= -1;
		k %= this.length;
		return k;
	}

	/**
	*	collision strategy:
	*	@param valueStringKey
	*	@param originalHash
	*	@return int: a new hash value
	*/
	private int rehashCollision (int originalHash, int timesProbed) {
		return (int)(originalHash + 5*(timesProbed) + 7*(Math.pow(timesProbed, 2))) % this.length;
	}

	private void extendTable() {

	}


	public int length () {
		return length;
	}

	public void print() {
		int wc = 0;
		System.out.println("Table Length: " + this.length);
		System.out.println("Num of occupied slots: " + this.occupied);
		for (int i = 0; i < hashTable.length; i++) {
			ValueString curr = hashTable[i];
			while (curr != null) {
				wc++;
				System.out.println(curr + " in slot: " + i + "\t word #: " + wc);
				curr = curr.getNext();

			}
		}
	}
}
