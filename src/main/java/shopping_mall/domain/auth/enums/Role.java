package shopping_mall.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    USER("회원"), ADMIN("어드민"), SELLER("판매자");
    private final String role;

    public String getRole() {
        return "ROLE_" + this.role;
    }
}
