import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/** 
 * Array-based implementation of an indexed unsorted list. All objects are stored as elements
 * in an array of type T, where T is a generic representing any Object type specified by the 
 * user at instantiation. Overrides all methods from the IndexedUnsortedList interface. Contains
 * a private subclass "ALIterator" which defines methods to generate and manipulate an
 * iterator object over an ArrayList object.
 * 
 * @author Thomas Lonowski
 * @date 3/8/22
 * @param <T> generic placeholder for any Object type
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private T[] array;	// Underlying array that stores values added to the ArrayList
	private int rear;	// Points to the next available (empty) index in array
	
	/**
	 * IUArrayList constructor which instantiates a new array of type T with length given by
	 * parameter "size".
	 * 
	 * @param size the initial size of array specified by the user
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int size) {
		array = (T[])new Object[size];
		rear = 0;
	}
	
	/**
	 * Default IUArrayList constructor.
	 */
	public IUArrayList() {
		this(0);
	}	

	/**
	 * Helper method that checks if array is full and if so, creates a copy with 100 more indexes.
	 */
	private void expandIfNecessary() {
		if(rear == array.length) {
			array = Arrays.copyOf(array, array.length + 100);
		}
	}
	
	@Override
	public void addToFront(T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToRear(T element) {
		array[rear] = element;
		rear++;
	}

	@Override
	public void add(T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAfter(T element, T target) {
		// TODO Auto-generated method stub
		rear++;
	}

	@Override
	public void add(int index, T element) {
		// TODO Auto-generated method stub
		rear++;
	}

	@Override
	public T removeFirst() {
		// TODO Auto-generated method stub
		rear--;
		return null;
	}

	@Override
	public T removeLast() {
		if(!isEmpty()) {
			T retVal = array[rear];
			array[rear] = null;
			rear--;
			return retVal;
		}
		throw new NoSuchElementException();
	}

	@Override
	public T remove(T element) {
		// TODO Auto-generated method stub
		rear--;
		return null;
	}

	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		rear--;
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
		if(!isEmpty()) {
			for(int i = 0; i < rear; i++) {
				if(array[i].equals(element)) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public T first() {
		if(!isEmpty()) {
			return array[0];
		}
		throw new NoSuchElementException();
	}

	@Override
	public T last() {
		if(!isEmpty()) {
			return array[rear];
		}
		throw new NoSuchElementException();
	}

	@Override
	public boolean contains(T target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		return !(rear > 0);
	}

	@Override
	public int size() {
		return rear;
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

	private class ALIterator implements Iterator {

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object next() {
			// Use if statement with !hasNext to throw some error?
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void remove() {
			
		}
	}
}
