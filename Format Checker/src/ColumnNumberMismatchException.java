import java.io.IOException;

public class ColumnNumberMismatchException extends IOException {
	public ColumnNumberMismatchException(String message) {
		super(message);
	}
}