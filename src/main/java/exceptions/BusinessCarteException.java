package exceptions;

public class BusinessCarteException extends Exception {

    public BusinessCarteException(String message) {
        super(message);
    }

    public BusinessCarteException(String message, Throwable cause) {
        super(message, cause);
    }
}
