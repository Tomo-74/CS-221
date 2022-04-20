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
        
        IndexedUnsortedList<T> left = newList();
        IndexedUnsortedList<T> right = newList();
        
//        System.out.println("List size: " + list.size());
//        System.out.println("Size / 2: " + list.size() / 2);
        System.out.println("List: " + list);
        for(int i = 0; i < size / 2; i++) {		// Populate left list
        	left.add(list.removeFirst());
        }
        System.out.println("Left: " + left);
        
        for(int i = 0; i < size / 2; i++) {		// Populate right list
        	right.add(list.removeFirst());
        }
        System.out.println("Right: " + right + "\n");
        
        // Recursively sort left and right lists
        mergesort(left);
        //System.out.println("Left: " + left);
        mergesort(right);
        //System.out.println("Right: " + right);
        
        // Reassemble the final list
        while(!left.isEmpty() && !right.isEmpty()) {
            if(left.first().compareTo(right.first()) < 0) {
                list.add(left.removeFirst());
            } else {
                list.add(right.first());
            }
        }	
//        System.out.print("Reassembled List: " + list + "\n\n");
        while(!left.isEmpty()) {
        	list.add(left.removeFirst());
        }
        while(!right.isEmpty()) {
        	list.add(right.removeFirst());
        }
    }
    
    public static void main(String[] args) {
        IndexedUnsortedList<Integer> list = newList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        mergesort(list);
    }
}

