import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FormatChecker {
	private static int numRows, numCols; //, rowCounter, colCounter;
	private static String fileName;
	private static String[][] fileData;
	
	public FormatChecker() {
		numRows = numCols = 0;
		fileName = "";
		fileData = null;
	}
	
	/**
	 * 
	 * @param args files from command line
	 */
	@SuppressWarnings({ "resource", "finally" })
	public static void main(String[] files) {
		try { 
			if(files.length == 0) {	// Check if an input file is provided.
				throw new FileNotFoundException("Input file(s) not provided.");
			}
			
			for(int index = 0; index < files.length; index++) {	// Loop over each file
				try {
					if(index != 0) { System.out.println(); }	// Print blank line before each file (excluding the first)
					
					fileName = files[index];	// Find and print the current file's name
					System.out.println(fileName);
					
					Scanner fileScan  = new Scanner(new FileReader(files[index]));	// Scan the current file (break up rows into tokens). Throws FileNotFoundException if the file path is invalid
					String fileFormatLine = fileScan.nextLine();	// The first line of the file specifies the number of rows and columns
					
					// "^([0-9])(\\s++)([0-9])$"
					
					Pattern pattern = Pattern.compile("^([0-9])(\\s++)([0-9])$");	// Regular expression (regex) pattern: (one digit)(at least one whitespace character)(one digit)
					Matcher matcher = pattern.matcher(fileFormatLine);	// Create a Matcher object and search the first line for the regex
					boolean hasValidFirstLine = matcher.find();	// Evaluates to true if the first line contains the regex
					System.out.println("Has valid first line: " + hasValidFirstLine);	// For debugging
					
					if(!hasValidFirstLine) {	// If the first line is invalid, throw an InvalidFirstLineException
						throw new InvalidFirstLineException("First line of file does not contain two white-space-separated positive integers");						
					}
					
					// If the above if statement is skipped, we know the first line is valid. Therefore, we can parse the number of rows and columns from Strings to ints
					numRows = Integer.parseInt(matcher.group(1));	// The regex's first group contains the first digit, representing the number of rows in the file
					numCols = Integer.parseInt(matcher.group(3));	// The regex's second group contains the second digit, representing the number of columns in the file
					fileData = new String[numRows][numCols];
					
					String line = fileScan.nextLine();
					Scanner rowScan = new Scanner(line);
					
					for(int row = 0; row < numRows; row++) {
						for(int col = 0; col < numCols; col++) {
							fileData[row][col] = rowScan.next();
						}
						rowScan.nextLine();
					}
					/*
					while (fileScan.hasNextLine()) {
						String row = fileScan.nextLine();
						rowCounter++;	// Track the number of rows in the file
						Scanner rowScan = new Scanner(row); // Scanner to break current row into tokens
						while (rowScan.hasNext()) {	// While there are still characters in the row...						
							colCounter++;	// Track the number of characters (columns) in the current row
							String curValue = rowScan.next();	// Moves the scanner to the next column
							// System.out.println(curValue);
							boolean isAlphabetic = curValue.matches("[a-zA-Z]+");
							
							if(isAlphabetic) {
								throw new InputMismatchException("Invalid data type in file body. Expected numeric value, but received: \"" + curValue + "\"");
							}
						}
						rowScan.close();
						
						if(colCounter != 0) {
							if(numCols != colCounter) {	// Check that the file has the number of columns specified by the first line
								throw new ColumnMismatchException("Specified number of columns does not match the actual number of columns");
							}
						}
						colCounter = 0;	// Reset the column counter before Scanning the next row
					}
					if(numRows != rowCounter) {	// Check that the file has the number of rows specified by the first line
						throw new RowMismatchException("specified number of rows does not match the actual number of rows");
					}
					*/
					System.out.println("VALID");	// If no Exception gets thrown, the file is valid (has good format and data)
				
				} catch(FileNotFoundException e) {	// Thrown if the Scanner cannot access the file (invalid file path)
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(InvalidFirstLineException e) {	// Thrown if the first line does not match the expected format (INT INT)
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(IndexOutOfBoundsException e) {
					System.out.println(e.toString());
					System.out.println("INVALID");
				}
				
				/*
				catch(InputMismatchException e) {	
					System.out.println(e.toString());	// Thrown when the file contains a non-numeric value
					System.out.println("INVALID");
				} catch(ColumnMismatchException e) {	// Thrown when the specified number of columns does not match the actual number of columns
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(RowMismatchException e) {	// Thrown when the specified number of rows does not match the actual number of rows
					System.out.println(e.toString());
					System.out.println("INVALID");
				}
				*/
				catch(Exception e) {
					System.out.println(e.toString());
					System.out.println("INVALID");
				} finally {
					// rowCounter = 0;	// Reset counter variables after reading through each file
					// colCounter = 0;	// In case an Exception is thrown in the while loop before colCounter is reset
					continue;	// Skip to the next file
				}
			} // end of for loop
		} catch(FileNotFoundException e) {	// Thrown if no file is provided through command line
			System.out.println(e.toString());
			System.out.println("INVALID");
		}
	}	// End of Class
}	// End of main() method
