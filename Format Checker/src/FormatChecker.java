import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * The FormatChecker Class evaluates the format of one or more input files, as specified in the command-line arguments.
 * If a file is correctly formatted, it will be declared VALID. Otherwise, it will be declared INVALID. The file testing
 * is performed in the main() method. Exception handling, in the form of throws statements and try/catch blocks, is used
 * to test the validity of a file's format. A file is valid if: 1) it's first line contains two white-space-separated,
 * positive integers (these integers represent the number of rows and columns to follow in the file); 2) the actual
 * number of rows and columns in the file matches the numbers specified on the first line; and 3) the file contains only
 * integers and/or doubles (excluding the first line, which must only contain integers). If these three conditions are met,
 * a given file is declared VALID.
 * 
 * @author Thomas Lonowski
 * @date 11 February 2022
 */
public class FormatChecker {
	//////////////////////////
	/// Instance Variables ///
	//////////////////////////
	private static int expectedRows, expectedCols;	// Will hold the expected number of rows and columns, as specified on the first line
	private static int actualRows, actualCols;	// Will hold the actual number of rows and columns, as determined by looping through the file
	private static String fileName;
	
	///////////////////
	/// Constructor ///
	///////////////////
	public FormatChecker() {
		expectedRows = expectedCols = actualRows = actualCols = 0;
		fileName = "";
	}

	/**
	 * The main() method determines if the provided files are of valid format. First, it checks that at least one input
	 * file has been provided. Then it uses two Scanner objects to read through each file. Using the scanners, it
	 * determines whether the first line is formatted correctly. If so, it loops through the rest of the file, checking
	 * that every value is numeric and that the actual number of rows and columns matches the expected number, as provided
	 * in the first line. If no Exception is thrown during these tests, then the file is valid.
	 * 
	 * @param files: array holding the files specified through the command line
	 * @throws FileNotFoundException, FirstLineException, ColumnMismatchException, RowMismatchException, Exception (all possible Exceptions are caught)
	 * @return void
	 */

	// @SuppressWarnings("resource")
	@SuppressWarnings("resource")
	public static void main(String[] files) {
		try { 
			if(files.length == 0) {	// Check if an input file is provided.
				throw new FileNotFoundException("File(s) not provided.");
			}
			
			for(int index = 0; index < files.length; index++) {	// Loop for each file
				try {
					System.out.println();	// Print a blank line before each file (for output readability)
					fileName = files[index];	// Find and print the current file's name
					System.out.println(fileName);
					
					Scanner fileScan  = new Scanner(new FileReader(files[index]));	// Scan the current file (each row is a token). Throws FileNotFoundException if the file path is invalid
					String fileFormatLine = fileScan.nextLine();	// Get the first line of the file, which specifies the number of rows and columns
					
					Pattern pattern = Pattern.compile("^([0-9])(\\s++)([0-9])$");	// Regular expression (regex): (one digit)(at least one whitespace character)(one digit)
																					// The first line must contain these and only these values to be considered properly formatted
					Matcher matcher = pattern.matcher(fileFormatLine);	// Create a Matcher object and search the first line for the regex
					boolean hasValidFirstLine = matcher.find();	// Evaluates to true if the first line contains the regex
					
					if(!hasValidFirstLine) {	// If the first line does not contain the regex, throw an InvalidFirstLineException
						throw new FirstLineException("First line of file does not contain two white-space-separated positive integers");						
					}
					
					// If the above if statement is skipped, we know the first line is valid. Therefore, we can parse the number of rows and columns from Strings to ints
					expectedRows = Integer.parseInt(matcher.group(1));	// The regex's first group contains the first digit, representing the number of rows in the file
					expectedCols = Integer.parseInt(matcher.group(3));	// The regex's third group contains the second digit, representing the number of columns in the file
					
					while (fileScan.hasNextLine()) {	// While the file has more rows...
						String row = fileScan.nextLine();	// Get the current row. Move the Scanner forward to the next row
						if(!row.trim().isEmpty()) {	// If the row is not blank...
							actualRows++;	// Increment the actual number of rows
							Scanner rowScan = new Scanner(row); // Scanner to break current row into tokens. This is for counting the number of columns in each row
							while (rowScan.hasNext()) {	// While there are still columns in the row...						
								String curValue = rowScan.next();	// Get the value in current column. Move the Scanner forward to the next column
								actualCols++;	// Increment the actual number of columns
								boolean isAlphabetic = curValue.matches("[a-zA-Z]+");	// Check if the value in the current column is alphabetic 
								
								if(isAlphabetic) {	// If the value in the current column is alphabetic, throw a DataTypeException
									rowScan.close();
									throw new DataTypeException("In file body: expected numeric value, but received \"" + curValue + "\"");
								}
							}
							rowScan.close();
						}	
					}
					System.out.println(expectedRows + " " + expectedCols);
					
					while (fileScan.hasNextLine()) {
						String row = fileScan.nextLine();
						actualRows++;	// Track the number of rows in the file
						
						///// DEBUG BLOCK /////
						System.out.println("Row: " + row);
						System.out.println("Row counter: " + actualRows);
						System.out.println();
						///// DEBUG BLOCK /////
						
						// oh my goodness use indexout of bounds exception when reading file content
						Scanner rowScan = new Scanner(row); // Scanner to break current row into tokens
						
						while (rowScan.hasNext()) {	// While there are still characters in the row...						
							actualCols++;	// Track the number of characters (columns) in the current row
							String curValue = rowScan.next();	// Moves the scanner to the next column
							
							// System.out.println(curValue);
							boolean isAlphabetic = curValue.matches("[a-zA-Z]");
							
							if(expectedCols != actualCols) {	// Check that the file actually has the number of columns specified by the first line
								throw new ColumnMismatchException("Expected " + expectedCols + " columns, but file contained " + actualCols);
							}
							actualCols = 0;	// Reset the column counter before Scanning the next row
						} 

						rowScan.close();
						
						// if(colCounter != 0) {
							if(expectedCols != actualCols) {	// Check that the file has the number of columns specified by the first line
								throw new ColumnMismatchException("Actual number of columns does not match specified number");
							}
						// }
							actualCols = 0;	// Reset the column counter before Scanning the next row
					}
					if(expectedRows != actualRows) {	// Check that the file actually has the number of rows specified by the first line
						throw new RowMismatchException("Expected " + expectedRows + " rows, but file contained " + actualRows);
					}
					
					System.out.println("VALID");	// If no Exception gets thrown during the above tests, then the file is valid (has good format and data)
				
				} catch(FileNotFoundException e) {	// Thrown if the Scanner cannot access the file (invalid file path)
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(FirstLineException e) {	// Thrown if the first line does not contain two white-space-separated, positive integers
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(DataTypeException e) {	// Thrown when a scanner encounters non-numeric data
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(ColumnMismatchException e) {	// Thrown if the specified number of columns does not match the actual number of columns
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(RowMismatchException e) {	// Thrown when the specified number of rows does not match the actual number of rows
					System.out.println(e.toString());
					System.out.println("INVALID");
				} catch(Exception e) {	// Catch-all for unexpected Exceptions
					System.out.println(e.toString());
					System.out.println("INVALID");
				} finally {	// Reset counter variables after reading through each file 
					actualRows = 0;
					actualCols = 0;	// actualCols is reset to 0 on line 96. But if an Exception is thrown during the while loop before line 96,
									// actualCols must be reset in this finally block
				}
			} // End of for loop
		} catch(FileNotFoundException e) {	// Thrown if at least one file is not provided through command line
			System.out.println(e.toString());
			System.out.println("INVALID");
		}
	}	// End of main() method
}	// End of FormatChecker Class
