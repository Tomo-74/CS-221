import java.io.IOException;

@SuppressWarnings("serial")
public class RowMismatchException extends IOException {
	public RowMismatchException(String message) {	// Exception message constructor
		super(message);
	}
}
