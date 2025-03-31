package shopping_mall.application.product.repository.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    READY("결제 중"),         // 주문 생성 후 결제 대기
    PAYMENT_COMPLETED("결제 완료"),       // 결제 완료됨
    PAYMENT_FAILED("주문 실패"),        // 주문 실패
    CANCELLED("주문 취소"),                // 주문 취소됨
    REFUNDED("환불 완료");                // 환불 완료됨

    private final String description;

    // 🔹 문자열을 Enum으로 변환하는 메서드
    public static OrderStatus fromString(String status) {
        return switch (status.toLowerCase()) {
            case "ready" -> READY;
            case "paid" -> PAYMENT_COMPLETED;
            case "failed" -> PAYMENT_FAILED;
            case "cancelled" -> CANCELLED;
            default -> throw new IllegalArgumentException("알 수 없는 결제 상태: " + status);
        };
    }
}
