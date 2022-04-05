import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 
 */

/**
 * @author Thomas Lonowski
 * @version Sp22 CS 221 Version 1.0
 * @date 4/5/21
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
	private DoubleNode<T> head;
	private DoubleNode<T> tail;
	private int size;
	private int modCount;
	
	/**
	 * Default constructor for a double linked list.
	 */
	public IUDoubleLinkedList() {
		head = tail = null;
		size = modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		DoubleNode<T> newNode = new DoubleNode<>(element);
		head.setPrevious(newNode);
		newNode.setNext(head);
		newNode.setPrevious(null);
		head = newNode;
		size++;
		modCount++;
		
		/*
		 * ListIterator<T> lit = listIterator();
		 * lit.add(element);
		 */
	}

	@Override
	public void addToRear(T element) {
		DoubleNode<T> newNode = new DoubleNode<>(element);
		tail.setNext(newNode);
		newNode.setPrevious(tail);
		newNode.setNext(null);
		tail = newNode;
		size++;
		modCount++;
		
		/*
		 * ListIterator<T> lit = listIterator(size);
		 * lit.add(element);
		 */
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		DoubleNode<T> newNode = new DoubleNode<>(element);
		DoubleNode<T> current = head;
		while(current != null && !current.getElement().equals(target)) {
			current = current.getNext();
		}
		if(current == null) {	// If list is empty or target is not found
			throw new NoSuchElementException();
		}
		newNode.setNext(current.getNext());
		newNode.setPrevious(current);
		if(current == tail) {
			current.setNext(newNode);
			tail = newNode;
		} else {
			current.getNext().setPrevious(newNode);
			current.setNext(newNode);
		}
		size++;
		modCount++;
		
		/*
		 * ListIterator<T> lit = listIterator();
		 * boolean foundTarget = false;
		 * while(lit.hasNext() && !foundIt){
		 * 		if(lit.next().equals(target)){
		 * 			lit.add(element);
		 * 			foundIt = true;
		 * 		}
		 * }
		 * 
		 * if(!foundIt){
		 * 		throw new NoSuchElementException();
		 * }
		 */
	}

	@Override
	public void add(int index, T element) {	// Try to avoid indexed methods with linked lists
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		DoubleNode<T> newNode = new DoubleNode<T>(element);
		if(index == 0) {	// Case: adding an element to the front of the list
			newNode.setNext(head);
			if(!isEmpty()) {
				head.setPrevious(newNode);
			}
			else {	// Case: adding to empty list []
				tail = newNode;
			}
			head = newNode;
		}
		else {
			// TODO make efficient
			if(index <= size/2) {	// Work from head
				// current = head;
			} else {	// Work from tail
				//current = tail
			}
			
			DoubleNode<T> current = head;
			for(int i = 0; i < index-1; i++) {
				current = current.getNext();	// Finds the element before index
			}
			// Update the node connections
			newNode.setNext(current.getNext());
			newNode.setPrevious(current);
			current.setNext(newNode);
			if(current != tail) {	
				newNode.getNext().setPrevious(newNode);
			}
			else {	// Case: adding an element to the end of the list
				tail = newNode;
			}
			
		}
		size++;
		modCount++;
		
		/*
		 * ListIterator<T> lit = listIterator(index);
		 * lit.add(element);
		 */
	}

	@Override
	public T removeFirst() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
		if(head == tail) {	// If it's a one element list [A]
			head = tail = null;
		} else {	// If it's a multi-element list [A, B, ...]
			head.getNext().setPrevious(null);
			head = head.getNext();
		}
		size--;
		modCount++;
		return retVal;
		
		/*
		 * ListIterator<T> lit = listIterator();
		 * 	T retVal = lit.next();
		 * 	lit.remove();
		 * 	return retVal;
		 */
	}

	@Override
	public T removeLast() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		T retVal = tail.getElement();
		tail = tail.getPrevious();
		if(tail == null) {	// Case: [A]
			head = null;
		} else {
			tail.setNext(null);
		}
		
		size--;
		modCount++;
		return retVal;
		
		/*
		 * ListIterator<T> lit = listIterator(size);
		 * T retVal = lit.previous();
		 * lit.remove();
		 * return retVal;
		 */
	}

	@Override
	public T remove(T element) {
		DoubleNode<T> current = head;
		while(current != null && !current.getElement().equals(element)) {	// Search until the element is found or the whole list is searched through
			current = current.getNext();
		}
		if(current == null) {	// If the element was not found or the list is empty
			throw new NoSuchElementException();
		}
		
		// Update node connections 
		if(current != tail) {
			current.getNext().setPrevious(current.getPrevious());
		} else {	// Case: removing the tail element
			tail = current.getPrevious();
		}
		if(current != head) {
			current.getPrevious().setNext(current.getNext());
		} else {	// Case: removing the head element
			head = current.getNext();
		}
		// At this point, no nodes point to current, so it has been removed
		
		size--;
		modCount++;
		return current.getElement();
	}

	@Override
	public T remove(int index) {
		if(index < 0 || index >= size) {	// Case: empty list []
			throw new IndexOutOfBoundsException();
		}
		T retVal = head.getElement();
		if(head == tail) {	// Case: one-element list [A]
			head = tail = null;
		} else {	// Case: multi-element list [A, B, ...]
			DoubleNode<T> current = head;
			for(int i = 0; i < index-1; i++) {		// Get element before element to remove
				current = current.getNext();
			}
			if(current == head && index != 1) {	// Case: removing head element
				head = head.getNext();
				head.setPrevious(current.getPrevious());
			} else {
				retVal = current.getNext().getElement();
				current.setNext(current.getNext().getNext());
				if(current.getNext() == null) {	// Case: removing tail element
					tail = current;
				} else {
					current.getNext().setPrevious(current);
				}
			}
		}
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(T element) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO
		return false;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		// TODO write method
		// use iterator methods, must be linear
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return new IUDLLiterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		return new IUDLLiterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		return new IUDLLiterator(startingIndex);
	}

	/**
	 * List iterator for an indexed unsorted Double Linked List. Also acts as basic Iterator.
	 */
	private class IUDLLiterator implements ListIterator<T> {
		private DoubleNode<T> nextNode;
		private int iterModCount;
		private int nextIndex;
		DoubleNode<T> lastReturned;
		
		/**
		 * Basic constructor, initializes iterator BEFORE first element.
		 */
		public IUDLLiterator() {
			this(0);
		}
		
		/**
		 * Indexed constructor, initializes iterator BEFORE startingIndex. Determines whether working from the front
		 * or the end of the list is more efficient.
		 * 
		 * @param startingIndex index that would be next
		 */
		public IUDLLiterator(int startingIndex) {
			if(startingIndex < 0 || startingIndex >= size) {
				throw new IndexOutOfBoundsException();
			}
			iterModCount = modCount;
			nextNode = head;
			if(startingIndex < size/2) {	// If you're working in the front of the list
				for(int i = 0; i < startingIndex; i++){
					nextNode = nextNode.getNext();
				}
			} else {
				if(startingIndex == size) {
					nextNode = null;
				} else {
					nextNode = tail;
					for(int i = 0; i < size-startingIndex-1; i++) {
						nextNode = nextNode.getPrevious();
					}
				}
					
			}
			nextIndex = startingIndex;
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
			lastReturned = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			return retVal;
		}
		
		@Override
		public boolean hasPrevious() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextNode != head;
		}

		@Override
		public T previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			if(nextNode == null) {	// If iterator is after the tail element
				nextNode = tail;	// Move it to the left
			} else {
				nextNode = nextNode.getPrevious();
			}
			lastReturned = nextNode;
			nextIndex--;
			return nextNode.getElement();
		}

		@Override
		public int nextIndex() {
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			return nextIndex-1;
		}

		@Override
		public void remove() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(lastReturned == null) {	// If next() or previous() has not been called
				throw new IllegalStateException();
			}
			
			if(lastReturned != tail) {
				lastReturned.getNext().setPrevious(lastReturned.getPrevious());
			} else {
				tail = lastReturned.getPrevious();
			}
			if(lastReturned != head) {
				lastReturned.getPrevious().setNext(lastReturned.getNext());
			} else {
				head = lastReturned.getNext();
			}
			if(lastReturned != nextNode) {	// If last move was next()
				nextIndex--;
			} else {	// Last move was previous()
				nextNode = nextNode.getNext();
			}
			size--;
			modCount++;
			iterModCount++;
			lastReturned = null;
//			nextIndex--;
		}

		@Override
		public void set(T element) {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			// TODO write this method
		}

		@Override
		public void add(T element) {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			DoubleNode<T> newNode = new DoubleNode<>(element);
			if(nextNode != null) {	// Case: not adding to tail
				newNode.setPrevious(nextNode.getPrevious());
				newNode.setNext(nextNode);
				nextNode.setPrevious(newNode);
			} else {	// Case: adding to tail end
				newNode.setPrevious(tail);
				tail = newNode;
			}
			if(newNode.getPrevious() != null) {
				newNode.getPrevious().setNext(newNode);
			} else {
				head = newNode;
			}
			size++;
			modCount++;
			iterModCount++;
			nextIndex++;
		}
	}
}
