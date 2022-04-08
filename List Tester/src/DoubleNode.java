/**
 * A double-linked Node for double-linked data structures
 * 
 * @author Thomas Lonowski
 * @date 3/17/22
 * @param T generic placeholder for any Object type
 */
public class DoubleNode<T> {
	private DoubleNode<T> next;	// Reference to the next node in the chain
	private DoubleNode<T> previous;
	private T element;		// Reference to the element that this particular node represents 

	/**
	 * Basic constructor to initialize a new Node with given element.
	 * 
	 * @param element the element to be referenced by this node
	 */
	public DoubleNode(T element) {
		this(element, null, null);
	}
	
	/**
	 * Constructor to initialize a new Node with given element as well as
	 * a pointer to the next and previous elements
	 * 
	 * @param element the element to be referenced by this node
	 * @param next the next element in the list
	 * @param previous the previous element in the list
	 */
	public DoubleNode(T element, DoubleNode<T> next, DoubleNode<T> previous) {
		this.element = element;
		this.next = next;
		this.previous = previous;
	}
	
	///////////////////////////
	/// GETTERS AND SETTERS ///
	///////////////////////////
	/**
	 * @return next the next node
	 */
	public DoubleNode<T> getNext() {
		return next;
	}
	
	/**
	 * @param next the next to set
	 */
	public void setNext(DoubleNode<T> next) {
		this.next = next;
	}
	
	/**
	 * @return previous the previous node
	 */
	public DoubleNode<T> getPrevious() {
		return previous;
	}
	
	/**
	 * @param next the next to set
	 */
	public void setPrevious(DoubleNode<T> previous) {
		this.previous = previous;
	}
	
	/**
	 * @return the element
	 */
	public T getElement() {
		return element;
	}
	
	/**
	 * @param element the element to set
	 */
	public void setElement(T element) {
		this.element = element;
	}
}
