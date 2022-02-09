import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("unused")
public class FormatChecker {
	private static int numRows;
	private static int numCols;
	private static int rowCounter;
	private static int colCounter;
	private static String fileName;
	private static boolean correctNumRows = false;
	private static boolean correctNumCols = false;
	
	private void printFile(Scanner fileScan, int numRows, int numCols) {
		for(int i = 0; i < numRows; i++) {		
			String line = fileScan.nextLine();
			Scanner lineScan = new Scanner(line);
			for(int j = 0; j < numCols; j++) {
				System.out.print(lineScan.next() + " ");
			}
			lineScan.close();
			System.out.println();
		}
		fileScan.close();
	}

	
	/**
	 * 
	 * @param args: input files specified through the command line
	 * @throws 
	 */
	public static void main(String[] files) {
		if(files.length == 0) {	// Check if an input file is provided.
			System.out.println("Input file(s) not provided.");
		} else {
			for(int index = 0; index < files.length; index++) {	// Scan each provided file
				numRows = 0;	// Reset for each file
				numCols = 0;
				
				if(index != 0) { System.out.println(); }	// Print a blank line before each file (excluding the first)
				
				// Find the current file's name in the file path:
				fileName = files[index];	
				System.out.println(fileName);
				
				try {
					Scanner fileScan  = new Scanner(new FileReader(files[index]));
					String fileFormat = fileScan.nextLine();	// The first line of the file, which specifies the number of rows and columns in the grid
					
					if(fileFormat.length() == 3 && fileFormat.substring(1, 2).equals(" ")) {	// Check that the first line of the file is correctly formatted
						numRows = Integer.parseInt(fileFormat.substring(0, 1));
						numCols = Integer.parseInt(fileFormat.substring(2, 3));				
					} else {
						// Generate custom Exception and throw it here. Catch down below.
						System.out.println("First line does not contain two white-space-separated positive integers");
						System.out.println("INVALID");
						continue;
					}
					
					while (fileScan.hasNextLine()) {
						String row = fileScan.nextLine();
						rowCounter++;
						Scanner rowScan = new Scanner(row); //Scanner to break current line into tokens
						while (rowScan.hasNext()) {							
							colCounter++;
							rowScan.next();
						}
						if(numCols != colCounter) {	// Check that the file has the specified number of columns
							throw new ColumnNumberMismatchException("Specified number of columns does not match actual number of columns");
						}
						// System.out.println(colCounter);
						colCounter = 0;
						rowScan.close();	// close rowScan earlier??
					}
					if(numRows != rowCounter) {	// Check that the file has the specified number of files
						throw new RowNumberMismatchException("Specified number of rows does not match actual number of rows");
					}
					
				} catch(FileNotFoundException e) {	// If the specified file cannot be found
					System.out.println(e.toString());
					System.out.println("INVALID");
					continue;
				} catch(NumberFormatException e) {	// Thrown if the file input cannot be parsed to an int
					System.out.println(e.toString());
					System.out.println("INVALID");
					continue;
				} catch(IndexOutOfBoundsException e) {	// Is this Exception check even necessary?
					System.out.println(e.toString());
					System.out.println("INVALID");
					continue;
				} catch(InputMismatchException e) {	// For when a double appears on the first line - necessary?
					
				} catch(ColumnNumberMismatchException e) {
					System.out.println(e.toString());
					System.out.println("INVALID");
					continue;
				} catch(RowNumberMismatchException e) {
					System.out.println(e.toString());
					System.out.println("INVALID");
					continue;
				}
				System.out.println("VALID");	// If no Exception is thrown, the file is valid (good format and data)
			}
		}
	}
}
