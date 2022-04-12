
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
	
	
}
