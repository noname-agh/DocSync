package pl.edu.agh.two.file;

public class FileOpenException extends Exception {

	public FileOpenException() {
		super();
	}

	public FileOpenException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileOpenException(String message) {
		super(message);
	}

	public FileOpenException(Throwable cause) {
		super(cause);
	}

}
