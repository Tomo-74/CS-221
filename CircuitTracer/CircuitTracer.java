import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {
	
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
		} else if(args[0] != "-s" || args[0] != "-q") {
			printUsage();
			return;
		} else if(args[1] != "-c" || args[0] != "-g") {
			printUsage();
			return;
		}
		
		// Initialize the Storage to use either a stack or queue
		if(args[0] == "-s") {
			Storage<Stack> stateStore = new Storage<>(Storage.DataStructure.stack);
		} else {
			Storage<Queue> stateStore = new Storage<>(Storage.DataStructure.queue);
		}
		
		// Read in the CircuitBoard from the given file
		try {
			CircuitBoard board = new CircuitBoard(args[0]);
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		//TODO: run the search for best paths
		
		//TODO: output results to console or GUI, according to specified choice
	}
	
} // class CircuitTracer
