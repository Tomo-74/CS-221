import java.util.*;

public class Practice {
	
	private static <T> IndexedUnsortedList<T> newList() 
	{
		return new WrappedDLL<T>(); //TODO: replace with your IUDoubleLinkedList for extra-credit
	}
	
	public static <T extends Comparable<T>> void sort(IndexedUnsortedList<T> list) 
	{
		mergesort(list);
	}
	
    private static <T extends Comparable<T>> void mergesort(IndexedUnsortedList<T> list) {
    	int size = list.size();
        
        if(size <= 1) {	// Base case
            return;
        }

        boolean listIsOdd = (size % 2 != 0);
        
        IndexedUnsortedList<T> left = newList();
        IndexedUnsortedList<T> right = newList();
        
        System.out.println("List: " + list);
    	for(int i = 0; i < size / 2; i++) {		// Populate left list
    		left.add(list.removeFirst());
    	}
        System.out.println("Left: " + left);
        
        if(listIsOdd) {
        	for(int i = 0; i < (size / 2) + 1; i++) {		// Populate right list with odd number of elements
        		right.add(list.removeFirst());
        	}
        	System.out.println("Right: " + right + "\n");
        } else {
        	for(int i = 0; i < size / 2; i++) {		// Populate right list
        		right.add(list.removeFirst());
        	}
        	System.out.println("Right: " + right + "\n");
        }
        
        // Recursively sort left and right lists
        mergesort(left);
        mergesort(right);

        // Reassemble the final list
    	while(!left.isEmpty() && !right.isEmpty()) {
    		if(left.first().compareTo(right.first()) < 0) {
    			list.add(left.removeFirst());
//    			list.add(right.removeFirst());
    		} else {
    			list.add(right.removeFirst());
//    			list.add(left.removeFirst());
    		}
    	}
    	
    	while(!left.isEmpty()) {
    		list.add(left.removeFirst());
    	}
    	while(!right.isEmpty()) {
    		list.add(right.removeFirst());
    	}
        System.out.print("Reassembled List: " + list + "\n\n");
    }
    
    public static void main(String[] args) {
        IndexedUnsortedList<Integer> list = newList();
        list.add(1);
        list.add(4);
        list.add(2);
        list.add(5);
        list.add(3);

        mergesort(list);
    }
}

