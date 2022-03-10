import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * Single-linked node implementation of IndexedUnsortedList
 * 
 * @author Thomas Lonowski
 * @date 3/8/22
 * @param <T> generic placeholder for any Object type
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private Node<T> head;
	private Node<T> tail;
	private int size;
	
	/**
	 * Default constructor: initializes a new, empty list
	 */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
	}
	
	@Override
	public void addToFront(T element) {		// O(1)
		Node<T> newNode = new Node<T>(element);
		if(isEmpty()) {
			tail = newNode;
		}
		head = newNode;
		size++;
	}

	@Override
	public void addToRear(T element) {
		Node<T> newNode = new Node<T>(element);
		if(isEmpty()) {
			head = newNode;
		} else {
			tail.setNext(newNode);			
		}
		tail = newNode;
		size++;
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
	public void add(int index, T element) {
		// TODO Auto-generated method stub
		
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
		return retVal;
	}

	@Override
	public T removeLast() {	// O(n)
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		T retVal = tail.getElement();	// Save the last element to return after removal
		
		if(size > 1) {
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
		return retVal;
	}

	@Override
	public T remove(T element) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
