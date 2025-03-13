package shopping_mall.application.auth.service.exception;

public class ValidRoleException extends RuntimeException {
    public ValidRoleException() {
        super("접근 권한이 없습니다.");
    }
}
