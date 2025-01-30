package data.Exceptions;

public class InvalidUserAccountException extends RuntimeException {
    public InvalidUserAccountException(String message) {
        super(message);
    }
}
