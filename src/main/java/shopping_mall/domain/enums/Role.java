package shopping_mall.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    USER("회원"), ADMIN("어드민"), SELLER("판매자");
    private final String role;
}
