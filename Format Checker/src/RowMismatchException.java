import java.io.IOException;

public class RowMismatchException extends IOException {
	public RowMismatchException(String message) {	// Exception message constructor
		super(message);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
