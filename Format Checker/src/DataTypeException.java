import java.io.IOException;

/**
 * Custom Exception Class. A DataTypeException is thrown when a file contains unexpected or invalid data.
 * This occurs when a Scanner encounters non-numeric data.
 * 
 * @author Thomas Lonowski
 * @date 11 February 2022
 * @extends IOException
 * 
 */
@SuppressWarnings("serial")
public class DataTypeException extends IOException {
	public DataTypeException(String message) {
		super(message);
	}
}
