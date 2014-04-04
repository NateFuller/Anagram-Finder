package anagrams;

public class MyLinkedList {
	private String key;
	private String value;
	private MyLinkedList next;

	public MyLinkedList (String key, String value) {
		this.key = key;
		this.value = value;
		this.next = null;
	}

	public void setNext (MyLinkedList newNext) {
		this.next = newNext;
	}

	public MyLinkedList getNext () {
		return this.next;
	}

	public void setValue (String newValue) {
		this.value = newValue;
	}

	public String getValue () {
		return this.value;
	}

	public String getKey () {
		return this.key;
	}
}