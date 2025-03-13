package shopping_mall.application.auth.service.exception;

public class LoginValidException extends RuntimeException {
    public LoginValidException() {
        super("존재하는 아이디가 없습니다.");
    }
}
