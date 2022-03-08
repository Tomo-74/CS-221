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
	 * Initializes a new, empty list
	 */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
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
	public void add(int index, T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T removeFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T removeLast() {
		// TODO Auto-generated method stub
		return null;
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
	public int indexOf(T element) {
//		int index = 0;
//		Node<T> currentNode = head;
//		
//		while(currentNode != null) {
//			if(currentNode.getElement().equals(element)) {
//				return index;
//			}
//			index++;
//			currentNode = currentNode.getNext();	// Write over current node's address with the next node's address
//		}
//		return -1;
		Node<T> currentNode = head;
		int returnIndex = -1;
		int currentIndex = 0;
		
		while(returnIndex < 0 && currentNode != null) {
			if(currentNode.getElement().equals(element)) {
				returnIndex = currentIndex;
			}
			currentIndex++;
			currentNode = currentNode.getNext();
		}
		return returnIndex;
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
								// Using head == null is fail-safe, because head is ONLY ever null when the list is empty
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
