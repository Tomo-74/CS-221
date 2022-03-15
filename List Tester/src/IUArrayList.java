import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
/** 
 * Array-based implementation of an indexed unsorted list. All objects are stored as elements
 * in an array of type T, where T is a generic representing any Object type specified by the 
 * user at instantiation. IUArrayList overrides all methods from the IndexedUnsortedList interface,
 * and contains a private subclass "ALIterator" which defines methods for iterating over an
 * ArrayList object. 
 * 
 * @author Thomas Lonowski
 * @date 3/10/22
 * @param <T> generic placeholder for any Object type
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private T[] array;	// Underlying array that stores values added to the ArrayList
	private int rear;	// Points to the next available (empty) index in array
	private int modCount;
	
	/**
	 * Default IUArrayList constructor.
	 */
	public IUArrayList() {
		this(10);	// Default list size
	}	
	
	/**
	 * Parameterized constructor: instantiates a new array of type T with length given by parameter "size"
	 * 
	 * @param size the initial size of array specified by the user
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int size) {
		array = (T[])new Object[size];
		rear = modCount = 0;
	}

	/**
	 * Helper method that checks if array is full and if so, creates a copy with doubled size.
	 */
	private void expandIfNecessary() {
		if(rear + 1 > array.length) {
			array = Arrays.copyOf(array, array.length * 2);
		}
	}
	
	@Override
	public void addToFront(T element) {
		add(0, element);
	}

	@Override
	public void addToRear(T element) {
		expandIfNecessary();
		array[rear] = element;
		rear++;
		modCount++;
	}

	@Override
	public void add(T element) {
		expandIfNecessary();
		array[rear] = element;
		rear++;
		modCount++;
	}

	@Override
	public void addAfter(T element, T target) {
		int index = indexOf(target);
		if(index == -1) {
			throw new NoSuchElementException();
		}
		add(index + 1, element);
	}

	@Override
	public void add(int index, T element) {
		if(index < 0 || index > rear) {	// Check that the index is accessible. This also guarantees that array is not empty.
			throw new IndexOutOfBoundsException();
		}
		expandIfNecessary();
		for(int i = rear; i > index; i--) {
			array[i] = array[i-1];
		}
		array[index] = element;
		rear++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return remove(0);
	}

	@Override
	public T removeLast() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		T removedVal = array[rear-1];
		array[rear-1] = null;
		rear--;
		modCount++;
		return removedVal;
	}

	@Override
	public T remove(T element) {
		int indexToRemove = indexOf(element);	// Will be a positive integer if element is found in array, -1 if not.
		if(indexToRemove >= 0) {
			return remove(indexToRemove);
		}
		throw new NoSuchElementException();
		
	}

	@Override
	public T remove(int index) {
		if(index < 0 || index >= rear) {	// Check that the index is accessible. This also guarantees that array is not empty.
			throw new IndexOutOfBoundsException();
		}
		T removedVal = array[index];
		for(int i = index; i < rear-1; i++) {	// Work backwards from the given index
			array[i] = array[i+1];	// Shift the values back by one index
		}
		rear--;
		array[rear] = null;	// Erase the last value, because after shifting it has a copy
		modCount++;
		return removedVal;
	}

	@Override
	public void set(int index, T element) {
		if(index < 0 || index >= rear) {	// Check that the index is accessible. This also guarantees that array is not empty.
			throw new IndexOutOfBoundsException();
		}
		array[index] = element;
		modCount++;	// Should set() be counted as a modification?
	}

	@Override
	public T get(int index) {
		if(index < 0 || index >= rear) {	// Check that the index is accessible. This also guarantees that array is not empty.
			throw new IndexOutOfBoundsException();
		}
		return array[index];
	}

	@Override
	public int indexOf(T element) {
		if(isEmpty()) {
			return -1;
		}
		for(int i = 0; i < rear; i++) {
			if(array[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public T first() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return array[0];
	}

	@Override
	public T last() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return array[rear-1];
	}

	@Override
	public boolean contains(T target) {
		if(indexOf(target) >= 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return rear == 0;
	}

	@Override
	public int size() {
		return rear;
	}

	@Override
	public String toString() {
		StringBuilder description = new StringBuilder();
		description.append('[');
		for(int i = 0; i < rear; i++) {
			description.append(array[i].toString() + ", ");
		}
		if(!isEmpty()) {
			description.delete(description.length()-2, description.length());	// If the list has elements, remove the trailing ", "
		}
		description.append(']');
		return description.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iterator() {
		return new ALIterator();
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
	 * An implementation of the Iteration interface for use with ArrayList objects. This particular
	 * implementation is fail-fast, meaning that a ConcurrentModificationException is thrown if the
	 * array is modified by anything other than the IUArrayList class itself and this particular
	 * ALIterator object.
	 * 
	 * @param <E> a generic Object type
	 */
	private class ALIterator implements Iterator<T> {
		int iterModCount;
		int nextIndex;
		boolean canRemove;
		
		public ALIterator() {
			iterModCount = modCount;
			nextIndex = 0;
			canRemove = false;
		}
		
		@Override
		public boolean hasNext() {
			if(!(iterModCount == modCount)) {	// Check for concurrent modification
				throw new ConcurrentModificationException();
			}
			return nextIndex < rear;
		}

		@Override
		public T next() {
			if(!(iterModCount == modCount)) {	// Check for concurrent modification
				throw new ConcurrentModificationException();
			}
			if(!hasNext()) {	// Check for remaining elements in the array
				throw new NoSuchElementException();
			}
			nextIndex++;	// Advance the iterator
			canRemove = true;	// Allow removal
			return array[nextIndex-1];	// Return the element that was iterated over
		}
		
		@Override
		public void remove() {
			if(!(iterModCount == modCount)) {	// Check for concurrent modification
				throw new ConcurrentModificationException();
			}
			if(!canRemove) {	// Check if next() has been called, thereby allowing remove() to be called
				throw new IllegalStateException();
			}
			for(int i = nextIndex-1; i < rear-1; i++) {	// Work backwards from the given index
				array[i] = array[i+1];	// Shift the values back by one index
			}
			iterModCount++;
			modCount++;
			rear--;
			nextIndex--;	// Retreat the iterator, since an element was removed
			array[rear] = null;	// Erase the last value, because after shifting it has a copy
			canRemove = false;
		}
	}
}
