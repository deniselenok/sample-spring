package delenok.task.exceptions;

public class BasicException extends RuntimeException {
    private static final long serialVersionUID = 7715767690483091764L;

    public BasicException(String message) {
        super(message);
    }

    public BasicException(String message, Throwable cause) {
        super(message, cause);
    }
}
