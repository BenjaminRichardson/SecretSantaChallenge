package circularlyLinkedList;

import java.util.*;

/***
 * This class is a circularly linked list of type T. It maintains the number of
 * elements and the "currentNode" in place of the head.
 * 
 * @param <T>
 */
public class CircularlyLinkedListManager<T> {

	private Node<T> currentNode;
	private int size;

	public CircularlyLinkedListManager() {
		this.currentNode = null;
		this.size = 0;
	}

	public int getSize() {
		return size;
	}

	// Adds node after current node
	public void addItem(T object) {
		Node<T> newNode = new Node<T>(object);
		if (size == 0) {
			newNode.setNext(newNode);
			newNode.setPrevious(newNode);
			currentNode = newNode;
		} else {
			Node<T> tempNode = currentNode.getNext();
			currentNode.setNext(newNode);
			newNode.setPrevious(currentNode);
			newNode.setNext(tempNode);
			tempNode.setPrevious(newNode);
		}
		size++;
	}

	// Removes current item from list. Whatever was after the removed node is
	// now the current node. If there is only a single item in the list, the
	// currentNode becomes null
	// Throws NoSuchElementException if you try to remove from an empty list.
	public void removeCurrentItem() {
		if (size == 0) {
			throw new NoSuchElementException();
		} else if (size == 1) {
			currentNode = null;
		} else {
			Node<T> prevNode = currentNode.getPrevious();
			Node<T> nextNode = currentNode.getNext();

			prevNode.setNext(nextNode);
			nextNode.setPrevious(prevNode);

			currentNode = nextNode;
		}
		size--;
	}

	public T getCurrentItem() {
		return currentNode.getObject();
	}

	// Goes a specified number of steps forward through the list
	// Steps must be positive.
	public void stepThroughList(int steps) {
		// There's no reason not to let you take 0 steps
		if (steps < 0) {
			throw new IllegalArgumentException("Must take a postive number of steps");
		}
		if (currentNode == null) {
			throw new IllegalStateException("There are no objects in the list to step through");
		}
		// steps mod size is effectively the same as taking that many steps in
		// the circular graph
		for (int i = 0; i < steps % size; i++) {
			currentNode = currentNode.getNext();
		}
	}
}
