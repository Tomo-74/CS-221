
/**
 * Custom Exception Class. A FirstLineException is thrown when the first line of a file
 * contains unexpected or invalid data. This occurs when the first line does not contain
 * two white-space-separated, positive integers
 * 
 * @author Thomas Lonowski
 * @date 11 February 2022
 * @extends DataTypeException, IOException
 * 
 */
@SuppressWarnings("serial")
public class FirstLineException extends DataTypeException {
	public FirstLineException(String message) {
		super(message);
	}
}
