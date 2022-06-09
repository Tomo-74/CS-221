import java.util.ArrayList;
import java.util.Stack;

public class Reverse<T> {

	public ArrayList<T> Reverse(ArrayList<T> array) {
		Stack<T> s = new Stack<>();
		
		// Populate stack with ArrayList elements
		for(int i = 0; i < array.size(); i++) {
			s.push(array.get(i));
		}
		
		ArrayList<T> reversedArray = new ArrayList<>();
		
		for(int i = 0; i < array.size(); i++) {
			reversedArray.add(s.pop());
		}
		
		return reversedArray;
	}
}
