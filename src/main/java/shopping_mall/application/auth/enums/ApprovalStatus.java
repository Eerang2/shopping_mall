package shopping_mall.application.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApprovalStatus {
    APPROVED("승인"), PENDING("대기"), REJECTED("거절");
    private final String status;
}
