// package anagrams;

public class ValueString {
	private String key;
	private String value;

	public ValueString (String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getValue () {
		return this.value;
	}

	public void setValue (String newValue) {
		this.value = newValue;
	}

	public String getKey () {
		return this.key;
	}
	
	public boolean equals (ValueString other) {
		return (this.key).equals(other.key);
	}

	public String toString() {
		return "word: " + this.value + ", key: " + key;
	}
}