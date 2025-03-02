package shopping_mall.domain.exception.registers;

public class PasswordValidateException extends RuntimeException {
    public PasswordValidateException(String message) {
        super (message);
    }
}
