package shopping_mall.application.auth.service.dto;

import lombok.Builder;
import lombok.Getter;
import shopping_mall.application.auth.enums.Role;

@Getter
@Builder
public class AuthUser {

    private Long key;
    private String id;
    private Role role;

    public static AuthUser of(Long key,String id, Role role) {
        return new AuthUser(key, id, role);
    }
}
