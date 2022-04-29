import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.List;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {
	
	// Object instance variables
	private CircuitBoard board;
	private Storage<TraceState> stateStore;
	
	// Individual X and Y coordinates of the starting and ending points
	private int startingPointX;
	private int startingPointY;
	
	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); //create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		System.out.println("Usage: java CircuitTracer <-s | -q> <-c | -g> input_file\n");
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 * @throws FileNotFoundException 
	 */
	public CircuitTracer(String[] args) {
		// Validate command line arguments:
		if (args.length != 3) {
			printUsage();
			return; // Exits the constructor immediately
		} else if(!args[0].equals("-s") && !args[0].equals("-q")) {
			printUsage();
			return;
		} else if(!args[1].equals("-c") && !args[1].equals("-g")) {
			printUsage();
			return;
		}
		
		try {
			// Read in the CircuitBoard from the given file
			CircuitBoard board = new CircuitBoard(args[2]);
			
			//TODO: run the search for best paths
		} catch(FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		/**
		 * TODO: Break up the following code into multiple try/catch blocks as needed:
		 */
			// Initialize the storage to use either a stack or queue
			if(args[0].equals("-s")) {
				stateStore = new Storage<>(Storage.DataStructure.stack);
			} else if(args[0].equals("-q")){
				stateStore = new Storage<>(Storage.DataStructure.queue);
			}
			
		try {
				
			List<TraceState> bestPaths = new ArrayList<>();	// Single linked list because it is efficient adding to rear and removing from front
			
			startingPointX = (int) board.getStartingPoint().getX() - 1;	// Subtract 1 because the Point class starts at index 1
			startingPointY = (int) board.getStartingPoint().getY() - 1;
			
			/* Make all possible moves from the starting position using the order: right, down, left, up.
			 * Store these initial moves in stateStore */
			if(board.isOpen(startingPointX, startingPointY + 1)) {
				stateStore.store(new TraceState(board, startingPointX, startingPointY + 1));
			}
			if(board.isOpen(startingPointX + 1, startingPointY)) {
				stateStore.store(new TraceState(board, startingPointX + 1, startingPointY));
			}
			if(board.isOpen(startingPointX, startingPointY - 1)) {
				stateStore.store(new TraceState(board, startingPointX, startingPointY - 1));
			}
			if(board.isOpen(startingPointX - 1, startingPointY)) {
				stateStore.store(new TraceState(board, startingPointX - 1, startingPointY));
			}
			
//			System.out.println("stateStore size: " + stateStore.size() + " \n");
//			System.out.println(stateStore.retrieve());
//			System.out.println(stateStore.retrieve());
			
			while(!stateStore.isEmpty()) {
				TraceState currentTrace = stateStore.retrieve();
				if(currentTrace.isComplete()) {
					if(bestPaths.isEmpty() || currentTrace.pathLength() == bestPaths.get(0).pathLength()) {
						bestPaths.add(currentTrace);
					} else if(currentTrace.pathLength() < bestPaths.get(0).pathLength()) {
						bestPaths.clear();
						bestPaths.add(currentTrace);
					} 
				} else {
					if(board.isOpen(currentTrace.getRow(), currentTrace.getCol() + 1)) {
						stateStore.store(new TraceState(currentTrace, currentTrace.getRow(), currentTrace.getCol() + 1));
						System.out.println("A: ");
					}
					if(board.isOpen(currentTrace.getRow() + 1, currentTrace.getCol())) {
						stateStore.store(new TraceState(currentTrace, currentTrace.getRow() + 1, currentTrace.getCol()));
						System.out.println("B: ");
					}
					if(board.isOpen(currentTrace.getRow(), currentTrace.getCol() - 1)) {
						stateStore.store(new TraceState(currentTrace, currentTrace.getRow(), currentTrace.getCol() - 1));
						System.out.println("C: ");
					}
					// for some reason this if statement evaluates to true when position contains a T
					System.out.println(board.isOpen(currentTrace.getRow() - 1, currentTrace.getCol()));
					if(board.isOpen(currentTrace.getRow() - 1, currentTrace.getCol())) {
						stateStore.store(new TraceState(currentTrace, currentTrace.getRow() - 1, currentTrace.getCol()));
						System.out.println("D: ");
					}
				}
			}
			
		} catch (InvalidFileFormatException e) {
			System.out.println(e.toString());
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.toString());
		} catch (OccupiedPositionException e) {
			System.out.println(e.toString());
		} catch (NullPointerException e) {
			System.out.println(e.toString());
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		
		//TODO: output results to console or GUI, according to specified choice
		
	}
	
} // class CircuitTracer
