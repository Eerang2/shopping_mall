package shopping_mall.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserGrade {
    REGULAR("일반 회원"), SILVER("실버 회원"), GOLD("골드 회원"), VIP("VIP 회원");

    private final String grade;
}
