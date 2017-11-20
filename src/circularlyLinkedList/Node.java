package circularlyLinkedList;

public class Node<T> {

	private T object; // never really a reason to change this
	private Node<T> next;
	private Node<T> previous;

	public Node(T object) {
		this.object = object;
		this.setNext(null);
	}

	public T getObject() {
		return this.object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public Node<T> getPrevious() {
		return previous;
	}

	public void setPrevious(Node<T> previous) {
		this.previous = previous;
	}

}
