import java.io.IOException;

/**
 * Custom Exception Class. A RowMismatchException is thrown when the actual number of rows
 * does not match the expected number of rows, as specified in the first line of the file.
 * 
 * @author Thomas Lonowski
 * @date 11 February 2022
 * @extends IOException
 * 
 */
@SuppressWarnings("serial")
public class RowMismatchException extends IOException {
	public RowMismatchException(String message) {	// Exception message constructor
		super(message);
	}
}
