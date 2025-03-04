package shopping_mall.presentation.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.model.User;

@Getter
public class UserReq {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Register {

        @NotBlank(message = "아이디는 필수입니다.")
        private String userId;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;

        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        private String confirmPassword;

        public boolean passwordMatch() {
            return !password.equals(confirmPassword);
        }

        public User toUser() {
            return User.builder()
                    .id(userId)
                    .password(password)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        private String userId;
        private String password;

        public User toUser() {
            return User.builder()
                    .id(userId)
                    .password(password)
                    .build();
        }
    }
}
