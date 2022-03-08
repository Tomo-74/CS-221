/**
 * 
 */

/**
 * A single-linked Node for single-linked data structures
 * 
 * @author Thomas Lonowski
 * @date 3/8/22
 * @param T generic placeholder for any Object type
 */
public class Node<T> {
	private Node<T> next;	// Reference to the next node in the chain
	private T element;		// Reference to the element this node represents 

	/**
	 * Constructor to initialize a new Node with given element
	 * @param element
	 */
	public Node(T element) {
		this(element, null);
	}
	
	/**
	 * Constructor to initialize a new Node with given element and next pointer
	 * @param element
	 * @param next
	 */
	public Node(T element, Node<T> next) {
		this.element = element;
		this.next = next;
	}
	
	/**
	 * @return the next
	 */
	public Node<T> getNext() {
		return next;
	}
	
	/**
	 * @param next the next to set
	 */
	public void setNext(Node<T> next) {
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
