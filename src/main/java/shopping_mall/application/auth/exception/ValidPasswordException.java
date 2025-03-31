package shopping_mall.application.auth.exception;

public class ValidPasswordException extends RuntimeException {
    public ValidPasswordException(String message) {
        super (message);
    }
}
