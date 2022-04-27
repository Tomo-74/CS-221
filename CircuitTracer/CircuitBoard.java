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
	/** location of row, col for '1' */
	private Point startingPoint;
	/** location of row, col for '2' */
	private Point endingPoint;

	// Instance variables for format checking
	private int expectedRows, expectedCols;		// The expected number of rows and columns, as specified by the input file's first line
	private int rows, cols;		// The actual number of rows and columns, as determined by looping through the file
	private final String ALLOWED_CHARS = "OXT12";
	
	// FINAL instance variables
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	
	// Counter variables to ensure valid number of 1s and 2s on board
	private int numOnes;
	private int numTwos;
	
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
	 * @param fileName file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String fileName) throws FileNotFoundException, InvalidFileFormatException {
		
		// Initialize instance variables
		rows = cols = 0;
		expectedRows = expectedCols = 0;		
		numOnes = numTwos = 0;
		
		/////////////////////////////////
		// Check for valid file format //
		/////////////////////////////////
			System.out.println();	// Print a blank line before each file (for output readability)
			Scanner fileScan  = new Scanner(new File(fileName));	// Scan the current file (each row is a token). Throws FileNotFoundException if the file path is invalid
			String formatLine = fileScan.nextLine();	// Get the first line of the file, which specifies the number of rows and columns
			
			Pattern pattern = Pattern.compile("^(\\d+)(\\s+)(\\d+)$");	// Regular expression (regex): (one digit)(at least one whitespace character)(one digit)
																		// The first line must contain these and only these values to be considered properly formatted
			Matcher matcher = pattern.matcher(formatLine);	// Create a Matcher object and search the first line for the regex
			boolean hasValidFirstLine = matcher.find();	// Evaluates to true if the first line contains the regex
			
			if(!hasValidFirstLine) {	// If the first line does not contain the regex, throw an InvalidFirstLineException
				throw new InvalidFileFormatException("First line of file does not contain two white-space-separated positive integers");						
			}
			
			// If the program gets to this point, we know that the first line is valid. Therefore, we can parse the number of rows and columns from Strings to ints
			expectedRows = Integer.parseInt(matcher.group(1));	// The regex's first group contains the first digit, representing the number of rows in the file
			expectedCols = Integer.parseInt(matcher.group(3));	// The regex's third group contains the second digit, representing the number of columns in the file
			
			int temp = 0;
			
			while (fileScan.hasNextLine()) {	// While the file has more rows...
				String row = fileScan.nextLine();	// Get the current row. Move the Scanner forward to the next row
				if(!row.trim().isEmpty()) {	// If the row is not blank...
					rows++;	// Increment the actual number of rows
					Scanner rowScan = new Scanner(row); // Scanner to break current row into tokens. This is for counting the number of columns in each row
					while (rowScan.hasNext()) {	// While there are still columns in the row...						
						String curElement = rowScan.next();	// Get the value in current column. Move the Scanner forward to the next column
						cols++;	// Increment the actual number of columns
//						System.out.println("Current element: " + curElement + " " + curElement.getClass().getName());
						
						if(!ALLOWED_CHARS.contains(curElement)) {	// If the current element is not a O, X, T, 1, or 2, throw an InvalidFileFormatException
							rowScan.close();
							throw new InvalidFileFormatException("In file body: expected only 'O' 'X' 'T' '1' '2', but received '" + curElement + "'");
						} else if(curElement.equals("1")) {
							numOnes++;
							if(numOnes > 1) {
								rowScan.close();
								throw new InvalidFileFormatException("More than one starting point ('1') in input file.");
							}
						} else if(curElement.equals("2")) {
							numTwos++;
							if(numTwos > 1) {
								rowScan.close();
								throw new InvalidFileFormatException("More than one starting point ('2') in input file.");
							}
						}
						
					}
					if(expectedCols != cols) {	// Check that the file actually has the number of columns specified by the first line
						rowScan.close();
						throw new InvalidFileFormatException("Expected " + expectedCols + " columns, but file contained " + cols);
					}
					temp = cols;	// Store the number of columns
					cols = 0;	// Reset the column counter before Scanning the next row
					rowScan.close();
				}	
			}
			if(expectedRows != rows) {	// Check that the file actually has the number of rows specified by the first line
				throw new InvalidFileFormatException("Expected " + expectedRows + " rows, but file contained " + rows);
			}
			
			/*
			 * Temp holds the number of columns in the file. This is true regardless of whether the
			 * file was invalid or not. The temp variable is necessary because cols is reset after
			 * every iteration through the inner while loop above (see line 106).
			 */
			cols = temp;	
			
//			System.out.println(expectedRows + " " + expectedCols);
//			System.out.println(rows + " " + cols);
		
		////////////////////
		// Populate board //
		////////////////////  
		Scanner rowScan = new Scanner(new File(fileName));	// Scan file to get contents
		rowScan.nextLine();	// Pass the first line
		
		board = new char[rows][cols];
		
		for(int i = 0; i < rows; i++) {	// Populate the board with the values from the input file		
			String line = rowScan.nextLine();
			Scanner lineScan = new Scanner(line);
			for(int j = 0; j < cols; j++) {
				board[i][j] = lineScan.next().charAt(0);
			}
			lineScan.close();
		}
		rowScan.close();
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		rows = original.numRows();
		cols = original.numCols();
	}

	/**
	 * Prints board to the console using a nested for-loop.
	 *
	 * @param board a board
	 */
	private void printBoard(char[][] board) {
		for(char[] row : board) {
			for(char n : row) {
				System.out.print(n + " ");
			}
			System.out.println();
		}
		System.out.println();	// Blank line for formatting
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
		return rows;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return cols;
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
	
	public static void main(String[] args) throws FileNotFoundException, InvalidFileFormatException {
		CircuitBoard myBoard = new CircuitBoard("C:\\Users\\tomol\\git\\EclipseProjects\\CircuitTracer\\invalid1.dat");
	}
}// class CircuitBoard
