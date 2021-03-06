import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * Single-linked node implementation of IndexedUnsortedList
 * 
 * @author Thomas Lonowski
 * @date 3/15/22
 * @param T generic placeholder for any Object type
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private SingleNode<T> head;
	private SingleNode<T> tail;
	private int size;
	private int modCount;
	
	/**
	 * Default constructor: initializes a new, empty list
	 */
	public IUSingleLinkedList() {
		head = tail = null;
		size = modCount = 0;
	}
	
	@Override
	public void addToFront(T element) {		// O(1)
		SingleNode<T> newNode = new SingleNode<T>(element);
		if(head == null) {
			tail = newNode;
		}
		head = newNode;
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {		//O(1)
		SingleNode<T> newNode = new SingleNode<T>(element);
		if(head == null) {
			head = newNode;
		} else {
			tail.setNext(newNode);			
		}
		tail = newNode;
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {		//O(n)
		SingleNode<T> targetNode = head;	// start at beginning
		while(targetNode != null && !targetNode.getElement().equals(target)) {	// search until the target element is found in the list
			targetNode = targetNode.getNext();	// targetNode becomes null if it reaches the end of the list
		}
		if(targetNode == null) {	// Occurs when the element isn't found in the list
			throw new NoSuchElementException();
		}
		SingleNode<T> newNode = new SingleNode<T>(element);
		newNode.setNext(targetNode.getNext());	// Order cannot be switched, because targetElement would lose its reference to the next element
		targetNode.setNext(newNode);
		if(targetNode == tail) {	// If the new element was added after tail, update tail to point at the new element
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		SingleNode<T> newNode = new SingleNode<T>(element);
		SingleNode<T> current = head;
		if(head == null) {
			newNode.setNext(null);
			head = newNode;
		} else {	// Case: list size > 2
			for(int i = 0; i < index-1; i++) {	// Find the element before the element at index
				current = current.getNext();	// current will never be null because index is guaranteed to be in bounds
			}
			if(current != null) {
				newNode.setNext(current.getNext());
				current.setNext(newNode);
			}
		}
		if(current == tail) {
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public T removeFirst() {	// O(1)
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
		head = head.getNext();
		if(head == null) {	// Case: 1-element list. Head gets set to null above, now tail must also be set to null
			tail = null;
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T removeLast() {	// O(n) because you need to find a reference to the second-to-last element
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();	// Save the last element to return after removal
		if(size > 1) {	// If it's not empty or a 1-element list
			SingleNode<T> newTail = head;
			while(newTail.getNext() != null && newTail.getNext() != tail) {
				newTail = newTail.getNext();
			}
			newTail.setNext(null);
			tail = newTail;
		} else {	// If it's a 1-element list, simply delete the element
			head = tail = null;
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		if(head == null) {
			throw new NoSuchElementException();
		}
		T retVal;
		if(head.getElement().equals(element)) {	// If the element to remove is the head element
			retVal = head.getElement();
			head = head.getNext();	// Shift head forward 1 element
			if(head == null) {	// If it was also a 1-element list
				tail = null;
			}
		}
		else {
			SingleNode<T> current = head;
			while(current.getNext() != null && !current.getNext().getElement().equals(element)) {	// Loop until you find index in the list
				current = current.getNext();
			}
			if(current.getNext() == null) {	// If the element at index was not in the list, throw an Exception
				throw new NoSuchElementException();
			}
			retVal = current.getNext().getElement();
			if(current.getNext() == tail) {	// If you're removing the tail element
				tail = current;
			}
			current.setNext(current.getNext().getNext());
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(int index) {
		if(index < 0 || index >= size) {	// Catches empty list
			throw new IndexOutOfBoundsException();
		}
		T retVal;
		if(index == 0) {	// If removing head element
			retVal = head.getElement();
			head = head.getNext();
			if(head == null) {	// If it also a 1-element list, then head just got set to null. So you must also set tail to null 
				tail = null;
			}
		} else {
			SingleNode<T> current = head;
			for(int i = 0; i < index-1; i++) {
				current = current.getNext();
			}
			retVal = current.getNext().getElement();
			if(current.getNext() == tail) {	// If removing tail element, set tail to the current node, which is the node before the node to remove
				tail = current;
			}
			current.setNext(current.getNext().getNext());
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		if(index < 0 || index >= size) {	// Catches invalid indexes (this includes empty lists)
			throw new IndexOutOfBoundsException();
		}
		SingleNode<T> newNode = new SingleNode<T>(element);
		SingleNode<T> current = head;
		if(index == 0) {	// Case: setting the head element
			newNode.setNext(current.getNext());
			head = newNode;
		} else {
			for(int i = 0; i < index-1; i++) {	// Finds the element before the element at index
				current = current.getNext();	// current will never be null because index is guaranteed to be in bounds
			}
			newNode.setNext(current.getNext().getNext());
			current.setNext(newNode);
		}
		if(newNode.getNext() == null) {	// Case: setting the tail element / 1-element list
			tail = newNode;
		}
		modCount++;
	}

	@Override
	public T get(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		SingleNode<T> targetNode = head;
		for(int i = 0; i < index; i++) {
			targetNode = targetNode.getNext();
		}
		return targetNode.getElement();
	}

	@Override
	public int indexOf(T element) {	// O(n)
		SingleNode<T> current = head;
		int retVal = -1;
		int curVal = 0;
		while(retVal < 0 && current != null) {
			if(current.getElement().equals(element)) {
				retVal = curVal;
			}
			curVal++;
			current = current.getNext();	// Write over current node's address with the next node's address
		}
		return retVal;
	}

	@Override
	public T first() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		   boolean contains = false;  
		   if(indexOf(target) >= 0) {
			   contains = true;
		   }
		   return contains;
	}

	@Override
	public boolean isEmpty() {
		return head == null;	// Using size == 0 requires you to accurately update size
								// Using head == null, however, is fail-safe because head is ONLY ever null when the list is empty
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		if(isEmpty()) {
			return "[]";
		}
		StringBuilder listDescription = new StringBuilder("[");
		SingleNode<T> current = head;
		while(current != null) {
			listDescription.append(current.getElement().toString() + ", ");
			current = current.getNext();
		}
		listDescription.delete(listDescription.length()-2, listDescription.length());
		listDescription.append("]");
		return listDescription.toString();
	}

	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Basic Iterator for IUSingleLinkedList
	 */
	private class SLLIterator implements Iterator<T>{
		private SingleNode<T> nextNode;
		private int iterModCount;
		private boolean canRemove;
		
		/**
		 * Initialize new iterator in front of the first element
		 */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
			canRemove = false;
		}

		@Override
		public boolean hasNext() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextNode != null;
		}

		@Override
		public T next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			
			T retVal = nextNode.getElement();
			nextNode = nextNode.getNext();
			canRemove = true;
			return retVal;
		}
		
		@Override
		public void remove() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(!canRemove) {
				throw new IllegalStateException();
			}
			if(head.getNext() == nextNode) {	// Case: removing head element
				head = head.getNext();
				if(head == null) {	// Case: one-element list where the element is being removed. Once the value is removed by setting head to null, tail must also be set to null.
					tail = null;
				}
			} else {
				SingleNode<T> current = head;
				while(head.getNext().getNext() != nextNode) {	// Must find the node two elements behind next node because the element behind nextNode is the one to remove
					current = current.getNext();
				}
				current.setNext(nextNode);
				if(nextNode == null) {	// If the tail was removed, nextNode is null, and tail needs to be set to the previous element
					tail = current;	
				}
			}
			size--;
			modCount++;
			iterModCount++;
			canRemove = false;
		}
		
	}

}
