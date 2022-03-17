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
	private T element;		// Reference to the element this node represents 

	/**
	 * Constructor to initialize a new Node with given element
	 * @param element
	 */
	public DoubleNode(T element) {
		this(element, null, null);
	}
	
	/**
	 * Constructor to initialize a new Node with given element and next pointer
	 * @param element
	 * @param next
	 */
	public DoubleNode(T element, DoubleNode<T> next, DoubleNode<T> previous) {
		this.element = element;
		this.next = next;
		this.previous = previous;
	}
	
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
