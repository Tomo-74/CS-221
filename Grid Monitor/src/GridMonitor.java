// Package imports:
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * The GridMonitor class determines if a solar array ("grid") is in danger of exploding. 
 * 
 * The solar array is represented by input from a text file, which is read in and used to populate a 2D array (baseGrid).
 * The class's methods perform various operations on the elements in baseGrid to determine if the grid is in danger of exploding.
 * These operations include calculating the sum of the surrounding elements, calculating the average of that sum, identifying the
 * delta range in which an element is safe, and testing whether or not each element is within that range. The result of each of
 * these calculations is stored in a new 2D array, named for the calculation it represents (see instance variables).
 *  
 * @author Thomas Lonowski
 * @version GridMonitor 1.0
 * @date 17 January 2022
 * @GitHub Tomo-74/EclipseProjects
 */
public class GridMonitor implements GridMonitorInterface {
	////////////////////////
	// Instance Variables //
	////////////////////////
	private double[][] baseGrid;
	private double[][] sumGrid;
	private double[][] avgGrid;
	private double[][] deltaGrid;
	private boolean[][] dangerGrid;
	private int numRows;
	private int numCols;
	
	/////////////////
	// Constructor //
	/////////////////
	/**
	 * Reads in input from a text file using two scanners. The first line in the text file designates the number of rows and columns to follow.
	 * This information is used to initialize all of the grids to the correct size. baseGrid is populated with the contents of the text file.
	 * 
	 * @param a String representing a file name
	 * @throws FileNotFoundException if the specified file is not found
	 */
	public GridMonitor(String filename) throws FileNotFoundException { 
		Scanner fileScan = new Scanner(new File(filename));	// Scanner to read in file input
		String arrayInfo = fileScan.nextLine();	// arrayInfo represents the file's first line, which specifies the number of rows and columns in the grid
		
		numRows = Integer.parseInt(arrayInfo.substring(0, 1));
		numCols = Integer.parseInt(arrayInfo.substring(2, 3));
		
		baseGrid = new double[numRows][numCols];	// Initialize all the 2D arrays. Their size is designated by the grid dimensions specified in the text file
		sumGrid = new double[numRows][numCols];
		avgGrid = new double[numRows][numCols];
		deltaGrid = new double[numRows][numCols];
		dangerGrid = new boolean[numRows][numCols];
		
		for(int i = 0; i < numRows; i++) {	// Populate baseGrid with the values from the text file		
			String line = fileScan.nextLine();
			Scanner lineScan = new Scanner(line);
			for(int j = 0; j < numCols; j++) {
				baseGrid[i][j] = Double.parseDouble(lineScan.next());
			}
			lineScan.close();
		}
		fileScan.close();
	}
	
	/**
	 * Prints any grid in user-friendly format using a nested for loop.
	 *
	 * @param a grid
	 */
	private void printGrid(double[][] grid) {
		for(double[] row : grid) {
			for(double n : row) {
				System.out.print(n + " ");
			}
			System.out.println();
		}
		System.out.println();	// Blank line for formatting
	}
	
	
	/**
	 * Prints only the danger grid in user-friendly format using a nested for loop.
	 */
	private void printDangerGrid() {
		getDangerGrid();	// Ensure that dangerGrid has been populated with proper values. This is necessary because the user might call the methods out of order
		
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				System.out.print(dangerGrid[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Returns a copy of the original base grid, as read from file. This is for purposes of encapsulation.
	 * 
	 * @return copy of baseGrid
	 */
	public double[][] getBaseGrid(){
		double[][] copyBaseGrid = new double[numRows][numCols];
		
		for(int i = 0; i < numRows; i++) {	
			copyBaseGrid[i] = baseGrid[i].clone();	// Clone each row. This ensures that the copy doesn't point to the original, so changes to one won't affect the other
		}
		return copyBaseGrid;
	}
	
	
	/**
	 * Returns a grid with the same dimensions as the base grid, but each element is the sum of the 4 surrounding base elements.
	 * For elements on a grid border, the base element's own value is used when looking out of bounds, as if there is a mirror
	 * surrounding the grid. This method is exposed for testing.
	 * 
	 * @return grid containing the sum of adjacent positions
	 */
	public double[][] getSurroundingSumGrid(){
		double[][] sumGrid = new double[numRows][numCols];	// Reset sumGrid. This is so consecutive calls to this method don't keep increasing the elements' values
		
		for(int i = 0; i < numRows; i++) {	// Traverse through baseGrid			
			for(int j = 0; j < numCols; j++) {				
				try {	// Try to add the "above" element to sumGrid
					sumGrid[i][j] += baseGrid[i-1][j];
				} catch(ArrayIndexOutOfBoundsException e) {	// If the above element is out of bound, instead add the current element to sumGrid
					sumGrid[i][j] += baseGrid[i][j];
				}
				
				try {	// Try to add the "right" element to sumGrid
					sumGrid[i][j] += baseGrid[i][j+1];
				} catch(ArrayIndexOutOfBoundsException e) {	// If the right element is out of bound, instead add the current element to sumGrid
					sumGrid[i][j] += baseGrid[i][j];
				}
				
				try {	// Try to add the "below" element to sumGrid
					sumGrid[i][j] += baseGrid[i+1][j];
				} catch(ArrayIndexOutOfBoundsException e) {	// If the below element is out of bound, instead add the current element to sumGrid
					sumGrid[i][j] += baseGrid[i][j];
				}
				
				try {	// Try to add the "left" element to sumGrid
					sumGrid[i][j] += baseGrid[i][j-1];
				} catch(ArrayIndexOutOfBoundsException e) {	// If the left element is out of bound, instead add the current element to sumGrid
					sumGrid[i][j] += baseGrid[i][j];
				}				
			}			
		}		
		double[][] copySumGrid = new double[numRows][numCols];	// Create and return a copy of sumGrid, for purposes of encapsulation
		
		for(int i = 0; i < numRows; i++) {
			copySumGrid[i] = sumGrid[i].clone();	// Clone each row. This ensures that the copy doesn't point to the original, so changes to one won't affect the other
		}
		return copySumGrid;
	}
	
	
	/**
	 * Returns a grid with the same dimensions as the base grid, but each element is the average of the 4 surrounding base elements.
	 * This method is exposed for testing.
	 * 
	 * @return grid containing the average of adjacent positions
	 */
	public double[][] getSurroundingAvgGrid(){
		sumGrid = getSurroundingSumGrid();	// Ensure that sumGrid is assigned proper values for use later in this method. This is necessary because the user might call the methods out of order
		
		for(int i = 0; i < numRows; i++) {	
			avgGrid[i] = sumGrid[i].clone();	// Populate avgGrid with the clone of each row in sumGrid. This ensures that the two arrays don't point to each other, so changes to one won't affect the other
		}
		
		for(int i = 0; i < numRows; i++) {	// Traverse through avgGrid
			for(int j = 0; j < numCols; j++) {
				avgGrid[i][j] /= 4;	// Divide each element by 4 to calculate the average of the surrounding elements
			}
		}
		double[][] copyAvgGrid = new double[numRows][numCols];	// Create and return a copy of avgGrid, for purposes of encapsulation
		
		for(int i = 0; i < numRows; i++) {	
			copyAvgGrid[i] = avgGrid[i].clone();	// Clone each row. This ensures that the copy doesn't point to the original, so changes to one won't affect the other
		}	
		return copyAvgGrid;
	}
	
	
	/**
	 * Returns a grid with the same dimensions as the base grid, but each element is the maximum delta from the average.
	 * For example, if the surrounding average at some coordinate is 2.0 and the maximum delta is 50% of the average,
	 * the delta value at the same coordinate in this grid will be 1.0. This method is exposed for testing.
	 * 
	 * @return grid containing the maximum delta from average of adjacent positions
	 */
	public double[][] getDeltaGrid(){
		avgGrid = getSurroundingAvgGrid();	// Ensure that avgGrid is assigned proper values for use later in this method. This is necessary because the user might call the methods out of order
		
		for(int i = 0; i < numRows; i++) {	
			deltaGrid[i] = avgGrid[i].clone();	// Populate deltaGrid with the clone of each row in avgGrid. This ensures that the two arrays don't point to each other, so changes to one won't affect the other
			for(int j = 0; j < numCols; j++) {
				deltaGrid[i][j] = Math.abs(deltaGrid[i][j]);	// Take the absolute value of each deltaGrid element. This makes negative values positive, so the delta can be calculated (a negative delta wouldn't make any sense)
			}
		}

		for(int i = 0; i < numRows; i++) {	// Traverse through deltaGrid
			for(int j = 0; j < numCols; j++) {
				deltaGrid[i][j] /= 2;	// Divide each element by 2 to calculate the maximum deltas (50%)
			}
		}
		double[][] copyDeltaGrid = new double[numRows][numCols];	// Create and return a copy of deltaGrid, for purposes of encapsulation
		
		for(int i = 0; i < numRows; i++) {	
			copyDeltaGrid[i] = deltaGrid[i].clone();	// Clone each row. This ensures that the copy doesn't point to the original, so changes to one won't affect the other
		}	
		return copyDeltaGrid;
	}

	
	/**
	 * Returns a grid with the same dimensions as the base grid, but each element is a boolean value indicating if the cell is at risk.
	 * For example, if the cell at a coordinate is less than the surrounding average minus its maximum delta or greater than the surrounding
	 * average plus its maximum delta, the corresponding coordinate in this grid will be true. If the base cell value is within the safe
	 * range, however, the corresponding value in this grid will be false.
	 * 
	 * @return grid containing boolean values indicating whether the cell at this location is in danger of exploding
	 */
	public boolean[][] getDangerGrid(){
		deltaGrid = getDeltaGrid();	// Ensure that deltaGrid is assigned proper values for use later in this method. This is necessary because the user might call the methods out of order
		
		for(int i = 0; i < numRows; i++) {	// Traverse through deltaGrid
			for(int j = 0; j < numCols; j++) {
				double lowerBound = avgGrid[i][j] - deltaGrid[i][j];	// Calculate the delta's lower bound for each individual element
				double upperBound = avgGrid[i][j] + deltaGrid[i][j];	// Calculate the delta's upper bound for each individual element
				
				if(baseGrid[i][j] >= lowerBound && baseGrid[i][j] <= upperBound) {	// If the current element is within the delta bounds, it is not in danger of explosion
					dangerGrid[i][j] = false;
				} else {	// If the current element is outside of the delta bounds, it is in danger of explosion
					dangerGrid[i][j] = true;
				}
			}
		}
		boolean[][] copyDangerGrid = new boolean[numRows][numCols];	// Create and return a copy of dangerGrid, for purposes of encapsulation
		
		for(int i = 0; i < numRows; i++) {	
			copyDangerGrid[i] = dangerGrid[i].clone();	// Clone each row. This ensures that the copy doesn't point to the original, so changes to one won't affect the other
		}	
		return copyDangerGrid;
	}
	
	
	/**
	 * Labels and prints all of the grids. Returns a well-formatted, clearly labeled String with useful information about the GridMonitor.
	 * 
	 * @return a string identifying if the grid is safe or not
	 */
	public String toString() {		
		getDangerGrid();	// Ensure that dangerGrid is assigned proper values for use later in this method. This is necessary because the user might call the toString() before the above methods
		
		System.out.println("\n*******************************************************");	// Label and print all of the grids for user understanding and debugging
		System.out.println("Base grid:");	
		printGrid(baseGrid);
		
		System.out.println("Surrounding sum grid:");
		printGrid(sumGrid);
		
		System.out.println("Surrounding average grid:");
		printGrid(avgGrid);
		
		System.out.println("Delta grid:");
		printGrid(deltaGrid);
		
		System.out.println("Danger grid:");
		printDangerGrid();
		System.out.println("\n*******************************************************\n");
		
		// Check if the grid is in danger of exploding:
		String dangerMsg = "Grid safe.";	// Assume the grid is safe
		for(boolean[] row : dangerGrid) {	// Traverse through dangerGrid
			for(boolean danger : row) {
				if(danger == true) {	// dangerMsg remains "safe" unless one of the elements in dangerGrid is true
					dangerMsg = "Grid unsafe!";
				}
			}
		}
		return "<<<<< " + dangerMsg + " >>>>>";	// Return the String declaring whether or not the grid is safe
	}
}