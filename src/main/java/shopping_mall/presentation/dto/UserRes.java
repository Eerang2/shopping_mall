package shopping_mall.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import shopping_mall.domain.enums.Role;

@Getter
@Builder
public class UserRes {

    private Long key;
    private String token;
    private Role role;

    public static UserRes of(Long key, String token, Role role) {
        return UserRes.builder()
                .key(key)
                .token(token)
                .role(role)
                .build();
    }
}
