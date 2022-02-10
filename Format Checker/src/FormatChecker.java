import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FormatChecker {
	private static int numRows;
	private static int numCols;
	private static int rowCounter;
	private static int colCounter;
	private static String fileName;

	/**
	 * 
	 * @param args: input files specified through the command line
	 * @throws 
	 */
	@SuppressWarnings("resource")
	public static void main(String[] files) {
		try { 
			if(files.length == 0) {	// Check if an input file is provided.
				throw new FileNotFoundException("Input file(s) not provided.");
			}
			
			for(int index = 0; index < files.length; index++) {	// Loop over each provided file
				try {
					if(index != 0) { System.out.println(); }	// Print a blank line before each file (excluding the first)
					
					fileName = files[index];	// Find and print the current file's name
					System.out.println(fileName);
					
					Scanner fileScan  = new Scanner(new FileReader(files[index]));	// Scan the current file (break up rows into tokens)
																					// Throws FileNotFoundException if the file path is invalid
					String fileFormatLine = fileScan.nextLine();	// The first line of the file, which specifies the number of rows and columns in the file
					
					Pattern pattern = Pattern.compile("^(\\D*\\d){2}\\D*$");
					Matcher matcher = pattern.matcher(fileFormatLine);
					boolean twoNumsFound = matcher.find();
					// int didgetLocation = matcher.
					
					if(!fileFormatLine.contains("\s")) {
						throw new InvalidFirstLineException("First line of file does not contain two white-space-separated positive integers");						
					} else if(!twoNumsFound) {
						throw new InvalidFirstLineException("First line of file does not contain two integers.");
					} else {
						numRows = Integer.parseInt(fileFormatLine.strip().substring(0, 1));	// Throws NumberFormatException if not parseable
						numCols = Integer.parseInt(fileFormatLine.strip().substring(2, 3));
					}
					
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
								throw new ColumnMismatchException("Actual number of columns does not match specified number");
							}
						}
						colCounter = 0;	// Reset the column counter before Scanning the next row
					}
					if(numRows != rowCounter) {	// Check that the file has the number of rows specified by the first line
						throw new RowMismatchException("Actual number of rows does not match specified number");
					}
					System.out.println("VALID");	// If no Exception gets thrown, the file is valid (has good format and data)
				
				} catch(FileNotFoundException e) {	// Thrown if the Scanner cannot access the file (invalid file path)
					System.out.println(e.toString());
					System.out.println("INVALID");
					continue;	// Skip to the next file
				} catch(InvalidFirstLineException e) {	// Thrown if the first line does not match the expected format (INT INT)
					System.out.println(e.toString());
					System.out.println("INVALID");
					continue;
				} catch(NumberFormatException e) {	// Thrown if a value in the first line cannot be parsed to an int
					System.out.println(e.toString());
					System.out.println("INVALID");
					continue;
				} catch(InputMismatchException e) {	
					System.out.println(e.toString());	// Thrown when the file contains a non-numeric value
					System.out.println("INVALID");
					continue;
				} catch(ColumnMismatchException e) {
					System.out.println(e.toString());
					System.out.println("INVALID");
					continue;
				} catch(RowMismatchException e) {
					System.out.println(e.toString());
					System.out.println("INVALID");
					continue;
				} finally {
					rowCounter = 0;	// Reset counter variables after reading through each file
					colCounter = 0;	// In case an Exception is thrown in the while loop before colCounter is reset
				}
			} // end of for loop
		} catch(FileNotFoundException e) {	// Thrown if there is no file(s) provided through the command line
			System.out.println(e.toString());
			System.out.println("INVALID");
		}
	}	// End of Class
}	// End of main() method
