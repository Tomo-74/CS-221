import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 
 */

/**
 * @author Thomas Lonowski
 *
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
	private DoubleNode<T> head;
	private DoubleNode<T> tail;
	private int size;
	private int modCount;
	
	/**
	 * Default constructor for a double linked list
	 */
	public IUDoubleLinkedList() {
		head = tail = null;
		size = modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToRear(T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAfter(T element, T target) {
		// TODO Auto-generated method stub
		
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
	}

	@Override
	public T removeFirst() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		return head.getElement();
	}

	@Override
	public T last() {
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
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
	public Iterator<T> iterator() {
		return new IUDLLiterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		return new IUDLLiterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		return null;
	}

	/*
	 * List iterator IUDLL - also acts as basic Iterator
	 */
	private class IUDLLiterator implements ListIterator<T> {
		private DoubleNode<T> nextNode;
		private int iterModCount;
		private int nextIndex;
		private boolean canRemove;
		
		/*
		 * Basic constructor for DLL list iterator
		 */
		public IUDLLiterator() {
			nextNode = head;
			iterModCount = nextIndex = 0;
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
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public T previous() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
		}

		@Override
		public void set(T e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void add(T e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
