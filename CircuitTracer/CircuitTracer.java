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
	
	private CircuitBoard board;		// Represents the original board, from which traces are made
	private Storage<TraceState> stateStore;		/* A Storage object holding TraceState objects, each of which
												   represents a possible trace from the original board */
	
	/* Individual X and Y coordinates of the starting and ending points */
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
		/* Validate command line arguments */
		if (args.length != 3) {
			printUsage();
			return; // Exit the constructor
		} else if(!args[0].equals("-s") && !args[0].equals("-q")) {
			printUsage();
			return;
		} else if(!args[1].equals("-c") && !args[1].equals("-g")) {
			printUsage();
			return;
		}
		
		if(args[1].equals("-g")) {	// Inform user if GUI isn't implemented
			System.out.println("GUI not currently implemented. Please rerun program with -c argument for console output.");
			return;	// Exit the program
		}
		
		List<TraceState> bestPaths = new ArrayList<>();	// Single linked list because it is efficient adding to rear and removing from front

		try {
			board = new CircuitBoard(args[2]);	// Read in the CircuitBoard from the given file
		} catch (InvalidFileFormatException e) {
			System.out.println(e.toString());
			return;
		} catch (FileNotFoundException e) { 
			System.out.println(e.toString());
			return;
		}
		
		/* Initialize the storage to use either a stack or queue */
		if(args[0].equals("-s")) {
			stateStore = new Storage<>(Storage.DataStructure.stack);
		} else if(args[0].equals("-q")){
			stateStore = new Storage<>(Storage.DataStructure.queue);
		}
		
		startingPointX = (int) board.getStartingPoint().getX();
		startingPointY = (int) board.getStartingPoint().getY();
		
		try {
			/* Make all possible moves from the starting position using the order: right, down, left, up.
	   	   Store these initial moves in stateStore */ 
			if(board.isOpen(startingPointX, startingPointY + 1)) {
				stateStore.store(new TraceState(board, startingPointX, startingPointY + 1));	// Right
			}
			if(board.isOpen(startingPointX + 1, startingPointY)) {
				stateStore.store(new TraceState(board, startingPointX + 1, startingPointY));	// Down
			}
			if(board.isOpen(startingPointX, startingPointY - 1)) {
				stateStore.store(new TraceState(board, startingPointX, startingPointY - 1));	// Left
			}
			if(board.isOpen(startingPointX - 1, startingPointY)) {
				stateStore.store(new TraceState(board, startingPointX - 1, startingPointY));	// Up
			}
			
			while(!stateStore.isEmpty()) {
				
				TraceState currentTrace = stateStore.retrieve();	// Retrieve one TraceState from the top of the stack or the front of the queue
				
				if(currentTrace.isComplete()) {
					if(bestPaths.isEmpty() || currentTrace.pathLength() == bestPaths.get(0).pathLength()) {
						bestPaths.add(currentTrace);
					} else if(currentTrace.pathLength() < bestPaths.get(0).pathLength()) {
						bestPaths.clear();
						bestPaths.add(currentTrace);
					} 
				} else {
					CircuitBoard currentBoard = currentTrace.getBoard();
					int currentRow = currentTrace.getRow();
					int currentCol = currentTrace.getCol();
					
					if(currentBoard.isOpen(currentRow, currentCol + 1)) {
						stateStore.store(new TraceState(currentTrace, currentRow, currentCol + 1) );
					}
					if(currentBoard.isOpen(currentRow + 1, currentCol)) {
						stateStore.store(new TraceState(currentTrace, currentRow + 1, currentCol));
					}
					if(currentBoard.isOpen(currentRow, currentCol - 1)) {
						stateStore.store(new TraceState(currentTrace, currentRow, currentCol - 1));
					}
					if(currentBoard.isOpen(currentRow - 1, currentCol)) {
						stateStore.store(new TraceState(currentTrace, currentRow - 1, currentCol));
					}
				}
			}
		} catch (OccupiedPositionException e) {
			System.out.println(e.toString());
		}
		
		// Output best path(s) to console
		for(TraceState trace : bestPaths) {
			System.out.println(trace);
		}
	}
} // class CircuitTracer
