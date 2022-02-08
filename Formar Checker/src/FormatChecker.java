import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class FormatChecker {
	private static int numRows;
	private static int numCols;
	private static int rowCounter;
	private static int colCounter;
	
	/**
	 * 
	 * @param args: input files specified through the command line
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length > 0) {	// Check that at least one input file is provided
			try {
				Scanner fileScan  = new Scanner(new FileReader(args[0]));
				String fileFormat = fileScan.nextLine();	// The file's first line, which specifies the number of rows and columns in the grid
				
				// Surround with try/catch:
				numRows = Integer.parseInt(fileFormat.substring(0, 1));
				numCols = Integer.parseInt(fileFormat.substring(2, 3));
				
				for(int i = 0; i < numRows; i++) {		
					String line = fileScan.nextLine();
					Scanner lineScan = new Scanner(line);
					for(int j = 0; j < numCols; j++) {
						System.out.print(lineScan.next());
					}
					lineScan.close();
				}
				fileScan.close();
			} catch(FileNotFoundException e) {
				e.toString();
			/*
				while (fileScan.hasNextLine()) {
					line = fileScan.nextLine();
					if (line.length() > 0) {
						Scanner lineScan = new Scanner(line); //Scanner to break current line into tokens
						while (lineScan.hasNext()) {
							sentence += lineScan.next();
							if (lineScan.hasNext()) {
								sentence += " ";
							}
						}
						
						lineScan.close();
						if (fileScan.hasNextLine()) {
							sentence += " ";
						}
					}
				}
				System.out.println(sentence);
				fileScan.close();
			
			
		} else {
			System.out.println("Input file not provided.");
		}
		*/
	}

}
