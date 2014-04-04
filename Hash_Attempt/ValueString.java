//package anagrams;

public class ValueString {
	private String key;
	private String value;
	private ValueString next;

	public ValueString (String key, String value) {
		this.key = key;
		this.value = value;
		this.next = null;
	}

	

	public ValueString getNext () {
		return this.next;
	}

	public void setNext (ValueString newNext) {
		this.next = newNext;
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

	public String toString() {
		return "word: " + this.value + ", key: " + key;
	}
}