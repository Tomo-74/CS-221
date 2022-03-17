/**
 * A single-linked Node for single-linked data structures
 * 
 * @author Thomas Lonowski
 * @date 3/8/22
 * @param T generic placeholder for any Object type
 */
public class SingleNode<T> {
	private SingleNode<T> next;	// Reference to the next node in the chain
	private T element;		// Reference to the element this node represents 

	/**
	 * Constructor to initialize a new Node with given element
	 * @param element
	 */
	public SingleNode(T element) {
		this(element, null);
	}
	
	/**
	 * Constructor to initialize a new Node with given element and next pointer
	 * @param element
	 * @param next
	 */
	public SingleNode(T element, SingleNode<T> next) {
		this.element = element;
		this.next = next;
	}
	
	/**
	 * @return the next
	 */
	public SingleNode<T> getNext() {
		return next;
	}
	
	/**
	 * @param next the next to set
	 */
	public void setNext(SingleNode<T> next) {
		this.next = next;
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
