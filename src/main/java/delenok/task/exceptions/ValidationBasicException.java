package delenok.task.exceptions;

public class ValidationBasicException extends BasicException {

    private static final long serialVersionUID = 8058958798019393908L;

    public ValidationBasicException(String message) {
        super(message);
    }

    public ValidationBasicException(String message, Throwable cause) {
        super(message, cause);
    }
}
