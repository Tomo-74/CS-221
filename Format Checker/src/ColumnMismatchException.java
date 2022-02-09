import java.io.IOException;

public class ColumnMismatchException extends IOException {
	public ColumnMismatchException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return "java.io." + super.toString();
	}
}