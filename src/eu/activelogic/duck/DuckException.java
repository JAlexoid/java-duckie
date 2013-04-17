package eu.activelogic.duck;

/**
 * Duck'ing exception, for checking "duckability" of an object
 * or a set of object to an interface or object
 *
 * @author alex
 * @version 0.4
 */
public class DuckException extends RuntimeException {

	/**
	 * Default constructor
	 */
	public DuckException() {
	}

	/**
	 * Constructor with a message
	 *
	 * @param message
	 *            exception message
	 */
	public DuckException(String message) {
		super(message);
	}

	/**
	 * Constructor with a root cause
	 *
	 * @param cause
	 *            root cause
	 */
	public DuckException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with a message and root cause
	 *
	 * @param message
	 *            exception message
	 * @param cause
	 *            root cause
	 */
	public DuckException(String message, Throwable cause) {
		super(message, cause);
	}

}
