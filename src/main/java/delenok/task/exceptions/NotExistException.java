package delenok.task.exceptions;

public class NotExistException extends ValidationBasicException {

    private static final long serialVersionUID = 4601928594455215605L;

    public NotExistException(String message) {
        super(message);
    }

    public NotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
