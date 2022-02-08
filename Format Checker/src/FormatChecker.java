import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("unused")
public class FormatChecker {
	private static int numRows;
	private static int numCols;
	private static int rowCounter = 0;
	private static int colCounter = 0;
	private static String[] splitFilePath;
	private static String fileName;
	
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
			System.out.println("Input file not provided.");
		} else {
			for(int index = 0; index < files.length; index++) {	// Scan each provided file
				numRows = 0;	// Reset for each file
				numCols = 0;
				
				if(index != 0) { System.out.println(); }	// Print a blank line before each file (excluding the first)
				
				// Find the current file's name in the file path:
				splitFilePath = files[index].split("\\\\");
				fileName = splitFilePath[splitFilePath.length - 1];		
				System.out.println(fileName);
				
				try {
					Scanner fileScan  = new Scanner(new FileReader(files[index]));
					String fileFormat = fileScan.nextLine();	// The first line of the file, which specifies the number of rows and columns in the grid
					
					if(fileFormat.length() == 3 && fileFormat.substring(1, 2).equals(" ")) {	// Check that the first line of the file is correctly formatted
						numRows = Integer.parseInt(fileFormat.substring(0, 1));
						numCols = Integer.parseInt(fileFormat.substring(2, 3));				
					} else {
						// Throw and catch custom exception
						System.out.println("First line does not contain two white-space-separated positive integers");
						System.out.println("INVALID");
						continue;
					}
					
					// Change to for-loop:
					while (fileScan.hasNextLine()) {
						String line = fileScan.nextLine();
						if (line.length() > 0) {
							rowCounter++;
							Scanner lineScan = new Scanner(line); //Scanner to break current line into tokens
							while (lineScan.hasNext()) {
								colCounter++;
								lineScan.next();
							}
							lineScan.close();
						}
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
					
				}
				
				if(rowCounter != numRows || colCounter != numCols) {
					System.out.println("Fewer rows or columns than specified.");
					System.out.println("INVALID");
					continue;
				}
				System.out.println("VALID");	// Success
			}
		}
	}
}
