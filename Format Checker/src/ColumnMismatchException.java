import java.io.IOException;

/**
 * Custom Exception Class. A ColumnMismatchException is thrown when the actual number of columns
 * does not match the expected number of columns, as specified in the first line of the file.
 * 
 * @author Thomas Lonowski
 * @date 11 February 2022
 * @extends IOException
 * 
 */
@SuppressWarnings("serial")
public class ColumnMismatchException extends IOException {
	public ColumnMismatchException(String message) {
		super(message);
	}
}