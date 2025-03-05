package shopping_mall.domain.auth.exception.register;

public class MemberIdValidateException extends RuntimeException {
    public MemberIdValidateException(String message) {
        super(message);
    }
}
