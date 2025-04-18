package shopping_mall.presentation.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.application.auth.enums.Role;
import shopping_mall.application.auth.repository.entity.Admin;

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

        public Admin toEntity() {
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

        public Admin toEntity() {
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
    public static class Create {

        private String adminName;
        private String role;


        public Admin toEntity() {
            if (role.equals(Role.ADMIN.name())) {
                return Admin.builder()
                    .name(adminName)
                    .role(Role.ADMIN)
                    .build();
            }
            throw new IllegalArgumentException("권한이 알맞지 않습니다.");
        }
    }
}
