package shopping_mall.domain.auth.exception.register;

public class ValidRoleException extends RuntimeException {
    public ValidRoleException() {
        super("접근 권한이 없습니다.");
    }
}
