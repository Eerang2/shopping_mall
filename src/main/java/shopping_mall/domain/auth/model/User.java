package shopping_mall.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.auth.entity.UserEntity;
import shopping_mall.domain.auth.enums.Role;
import shopping_mall.domain.auth.enums.UserStatus;
import shopping_mall.domain.auth.exception.ValidMemberIdException;
import shopping_mall.domain.auth.exception.ValidPasswordException;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long key;
    private String id;
    private String password;
    private UserStatus status;
    private Role role;
    private LocalDateTime createdAt;
    private Long userGradeKey;

    public void validate() {
        if (!password.matches(".*\\d.*") || !password.matches(".*[A-Za-z].*")) {
            throw new ValidPasswordException("비밀번호는 숫자와 영문자, 특수문자를 포함해야합니다.");
        } else if (password.length() < 8 ) {
            throw new ValidPasswordException("비밀번호는 8자 이상입니다.");
        } else if (!id.matches("^[a-zA-Z0-9]*$")) {
            throw new ValidMemberIdException("아이디는 숫자와 영문자만 사용할수있습니다.");
        } else if (id.length() < 4 || id.length() > 12) {
            throw new ValidMemberIdException("아이디는 4자 이상 12자 이하입니다.");
        }
    }

    public static User of(String id, String password) {
        return new User(id, password, UserStatus.ACTIVE, Role.USER, 1L);
    }


    public User(String id, String password, UserStatus status, Role role, Long userGradeKey) {
        this.id = id;
        this.password = password;
        this.status = status;
        this.role = role;
        this.userGradeKey = userGradeKey;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .password(password)
                .status(status)
                .role(role)
                .userGradeKey(userGradeKey)
                .build();
    }
}
