public class Recursion {
	public static void main(String[] args) {
		String str = "Hello";
		printChars(str);
		System.out.println(reverseString(str));
	}
	
	private static void printChars(String str) {
		if(str.length() == 0) {
			return;
		}
		System.out.println(str.charAt(0));
		printChars(str.substring(1));
	}
	
	private static String reverseString(String str) {
		if(str.length() <= 1) {
			return str;
		}
		String reverseStr = "" + str.charAt(str.length()-1);
		reverseStr += reverseString(str.substring(str.length()-1));
		return reverseStr;
	}
	
	private static <T extends Comparable<T>> void quickSort(IndexedUnsortedList<T> list) {
		// Base case
		if(list.size() <= 1) {
			return;
		}
		
		// Grab pivot
		T pivot = list.removeFirst();
		
		//Partition remaining elements
		IndexedUnsortedList<T> left = newList();
		IndexedUnsortedList<T> right = newList();
		while(!list.isEmpty()) {
			if(list.first().compareTo(pivot) < 0) {	// first < pivot
				left.add(list.first());
			} else {
				right.add(list.first());
			}
		}
		
		// Recursively sort left and right lists
		quickSort(left);
		quickSort(right);
		
		// Reassemble the final list
		while(!left.isEmpty()) {
			list.add(left.removeFirst());
		}
		list.add(pivot);
		while(!right.isEmpty()) {
			list.add(right.removeFirst());
		}		
	}
	
	private static <T> void quickSort(IndexedUnsortedList<T> list, Comparator<T> c) {
		// Base case
		if(list.size() <= 1) {
			return;
		}
		
		// Grab pivot
		T pivot = list.removeFirst();
		
		//Partition remaining elements
		IndexedUnsortedList<T> left = newList();
		IndexedUnsortedList<T> right = newList();
		while(!list.isEmpty()) {
			if(c.compare(list.first(), pivot) < 0) {	// diff
				left.add(list.first());
			} else {
				right.add(list.first());
			}
		}
		
		// Recursively sort left and right lists
		quickSort(left, c);		// diff
		quickSort(right, c);	// diff
		
		// Reassemble the final list
		while(!left.isEmpty()) {
			list.add(left.removeFirst());
		}
		list.add(pivot);
		while(!right.isEmpty()) {
			list.add(right.removeFirst());
		}		
	}
}
