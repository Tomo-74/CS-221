import java.io.IOException;

@SuppressWarnings("serial")
public class ColumnMismatchException extends IOException {
	public ColumnMismatchException(String message) {
		super(message);
	}
}