import java.io.IOException;

public class InvalidFirstLineException extends IOException {
	public InvalidFirstLineException(String message) {
		super(message);
	}
}
