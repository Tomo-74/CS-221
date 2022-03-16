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
	private Node<T> head;
	private Node<T> tail;
	private int size;
	private int modCount;
	
	/**
	 * Default constructor: initializes a new, empty list
	 */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}
	
	@Override
	public void addToFront(T element) {		// O(1)
		Node<T> newNode = new Node<T>(element);
		if(isEmpty()) {
			tail = newNode;
		}
		head = newNode;
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {		//O(1)
		Node<T> newNode = new Node<T>(element);
		if(isEmpty()) {
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
		Node<T> targetNode = head;	// start at beginning
		while(targetNode != null && !targetNode.getElement().equals(target)) {	// search until the target element is found in the list
			targetNode = targetNode.getNext();	// targetNode becomes null if it reaches the end of the list
		}
		if(targetNode == null) {	// Occurs when the element isn't found in the list
			throw new NoSuchElementException();
		}
		Node<T> newNode = new Node<T>(element);
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
		Node<T> newNode = new Node<T>(element);
		Node<T> targetNode = head;
		int fromIndex = 0;
		while(fromIndex < index-1) {	// Find the element before the element at index
			targetNode = targetNode.getNext();	// targetNode will never be null because index is guaranteed to be in bounds
			fromIndex++;
		}
		newNode.setNext(targetNode.getNext());
		targetNode.setNext(newNode);
		if(targetNode == tail) {
			tail = newNode;
		}
		modCount++;
		size++;
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
			Node<T> newTail = head;
			while(newTail.getNext() != tail) {
				newTail = newTail.getNext();
			}
			tail.setNext(null);
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
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal;
		if(head.getElement().equals(element)) {	// If the element to remove if the head element
			retVal = head.getElement();
			head = head.getNext();	// Shift head forward 1 element
			if(head == null) {	// If it was also a 1-element list
				tail = null;
			}
		}
		else {
			// Need to find the element whose .getNext().getElement() is element
			Node<T> current = head;
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
		// TODO Auto-generated method stub
		modCount++;
		size--;
		return null;
	}

	@Override
	public void set(int index, T element) {
		// TODO Auto-generated method stub
	}

	@Override
	public T get(int index) {
		/*
		Node<T> targetNode = head;
		while() {	// search until the target element is found in the list
			targetNode = targetNode.getNext();	// targetNode becomes null if it reaches the end of the list
		}
		if(targetNode == null) {	// Occurs when the element isn't found in the list
			throw new NoSuchElementException();
		}
		*/
		return null;
	}

	@Override
	public int indexOf(T element) {	// O(n)
		Node<T> currentNode = head;
		int retIndex = -1;
		int curIndex = 0;
		
		while(retIndex < 0 && currentNode != null) {
			if(currentNode.getElement().equals(element)) {
				retIndex = curIndex;
			}
			curIndex++;
			currentNode = currentNode.getNext();	// Write over current node's address with the next node's address
		}
		return retIndex;
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
		// TODO Auto-generated method stub
		return false;
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
	 * Basic Iterator for IUSLL
	 */
	private class SLLIterator implements Iterator<T>{
		private Node<T> nextNode;
		private int iterModCount;
		
		/**
		 * Initialize new iterator in front of the first element
		 */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
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
			return retVal;
		}
		
		@Override
		public void remove() {
			// TODO Auto-generated method stub
		}
		
	}

}
