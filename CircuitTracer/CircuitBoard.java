import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		Scanner fileScan = new Scanner(new File(filename));
		
		//TODO: parse the given file to populate the char[][]
		// throw FileNotFoundException if Scanner cannot read the file
		// throw InvalidFileFormatException if any formatting or parsing issues are encountered
		
		ROWS = 0; //replace with initialization statements using values from file
		COLS = 0;
		
		fileScan.close();
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard


/*
 In constructor:
 		expectedRows = expectedCols = actualRows = actualCols = 0;
		fileName = "";
		
 In main method:
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
*/
