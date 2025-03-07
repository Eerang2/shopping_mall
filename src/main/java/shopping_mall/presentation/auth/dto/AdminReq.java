package shopping_mall.presentation.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.auth.model.Admin;

@Getter
public class AdminReq {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Register {

        private String adminId;
        private String password;
        private String confirmPassword;

        public boolean passwordMatch() {
            return password.equals(confirmPassword);
        }

        public Admin toModel() {
            return Admin.builder()
                    .id(adminId)
                    .password(password)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {

        private String adminId;
        private String password;

        public Admin toModel() {
            return Admin.builder()
                    .id(adminId)
                    .password(password)
                    .build();
        }
    }
}
