package shopping_mall.domain.auth.exception.register;

public class ValidPasswordException extends RuntimeException {
    public ValidPasswordException(String message) {
        super (message);
    }
}
