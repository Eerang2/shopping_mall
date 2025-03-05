package shopping_mall.domain.auth.exception.register;

public class PasswordValidateException extends RuntimeException {
    public PasswordValidateException(String message) {
        super (message);
    }
}
