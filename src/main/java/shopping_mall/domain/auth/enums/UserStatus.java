package shopping_mall.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE("활성"), SUSPENDED("휴면"), DELETED("탈퇴");
    private final String status;
}
