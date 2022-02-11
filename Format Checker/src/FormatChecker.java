import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FormatChecker {
	//////////////////////////
	/// Instance Variables ///
	//////////////////////////
	private static int numRows, numCols, rowCounter, colCounter;
	private static String fileName;
	
	///////////////////
	/// Constructor ///
	///////////////////
	public FormatChecker() {
		numRows = numCols = rowCounter = colCounter = 0;
		fileName = "";
	}
	
	/**
	 * 
	 * @param args files from command line
	 */
	public static void main(String[] files) {
		try { 
			if(files.length == 0) {	// Check if an input file is provided.
				throw new FileNotFoundException("Input file(s) not provided.");
			}
			
			for(int index = 0; index < files.length; index++) {	// Loop over each file
				try {
					if(index != 0) {System.out.println(); }	// Print blank line before each file (excluding the first)
					
					fileName = files[index];	// Find and print the current file's name
					System.out.println(fileName);
					
					Scanner fileScan  = new Scanner(new FileReader(files[index]));	// Scan the current file (break up rows into tokens). Throws FileNotFoundException if the file path is invalid
					String fileFormatLine = fileScan.nextLine();	// The first line of the file specifies the number of rows and columns
					
					Pattern pattern = Pattern.compile("^([0-9])(\\s++)([0-9])$");	// Regular expression (regex) pattern: (one digit)(at least one whitespace character)(one digit)
					Matcher matcher = pattern.matcher(fileFormatLine);	// Create a Matcher object and search the first line for the regex
					boolean hasValidFirstLine = matcher.find();	// Evaluates to true if the first line contains the regex
					// System.out.println("Has valid first line: " + hasValidFirstLine);	// For debugging
					
					if(!hasValidFirstLine) {	// If the first line is invalid, throw an InvalidFirstLineException
						throw new FirstLineException("First line of file does not contain two white-space-separated positive integers");						
					}
					
					// If the above if statement is skipped, we know the first line is valid. Therefore, we can parse the number of rows and columns from Strings to ints
					numRows = Integer.parseInt(matcher.group(1));	// The regex's first group contains the first digit, representing the number of rows in the file
					numCols = Integer.parseInt(matcher.group(3));	// The regex's second group contains the second digit, representing the number of columns in the file
					// System.out.println("Expected rows: " + numRows + ", expected columns: " + numCols);
					
					while (fileScan.hasNextLine()) {
						String row = fileScan.nextLine();
						// System.out.println("\n" + row);
						if(!row.trim().isEmpty()) {	// If the current line is not blank...
							rowCounter++;	// Track the number of rows in the file
							// System.out.println("Row count: " + rowCounter);
							Scanner rowScan = new Scanner(row); // Scanner to break current row into tokens
							while (rowScan.hasNext()) {	// While there are still characters in the row...						
								String curValue = rowScan.next();	// Moves the scanner to the next column
								colCounter++;	// Track the number of characters (columns) in the current row
								boolean isAlphabetic = curValue.matches("[a-zA-Z]+");
								
								if(isAlphabetic) {
									rowScan.close();
									throw new DataTypeException("In file body: expected numeric value, but received \"" + curValue + "\"");
								}
							}
							rowScan.close();
							// System.out.println(colCounter + " columns in row " + rowCounter);
							
							if(numCols != colCounter) {	// Check that the file has the number of columns specified by the first line
								throw new ColumnMismatchException("Expected " + numCols + " columns, but file contained " + colCounter);
							}
							colCounter = 0;	// Reset the column counter before Scanning the next row
						} else {	// If the current line is blank...
							continue;	// Skip to the next line
						}
					}
					if(numRows != rowCounter) {	// Check that the file has the number of rows specified by the first line
						throw new RowMismatchException("Expected " + numRows + " rows, but file contained " + rowCounter);
					}
					
					System.out.println("VALID");	// If no Exception gets thrown, the file is valid (has good format and data)
				
				} catch(FileNotFoundException e) {	// Thrown if the Scanner cannot access the file (invalid file path)
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(FirstLineException e) {	// Thrown if the first line does not match the expected format (INT INT)
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(ColumnMismatchException e) {	// Thrown when the specified number of columns does not match the actual number of columns
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(RowMismatchException e) {	// Thrown when the specified number of rows does not match the actual number of rows
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(Exception e) {
					System.out.println(e.toString());
					System.out.println("INVALID");
				} finally {
					rowCounter = 0;	// Reset counter variables after reading through each file 
					colCounter = 0;	// colCounter is reset to 0 on line 80, but if an Exception is thrown beforehand then it must be reset in the finally block instead
				}
			} // end of for loop
		} catch(FileNotFoundException e) {	// Thrown if no file is provided through command line
			System.out.println(e.toString());
			System.out.println("INVALID");
		}
	}	// End of Class
}	// End of main() method
