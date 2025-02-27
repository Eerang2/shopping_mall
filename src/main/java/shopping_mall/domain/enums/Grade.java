package shopping_mall.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {
    REGULAR("일반 회원"), SILVER("실버 회원"), GOLD("골드 회원"), VIP("VIP 회원");

    private final String grade;
}
