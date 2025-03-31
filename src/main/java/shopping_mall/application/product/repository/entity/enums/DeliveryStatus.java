package shopping_mall.application.product.repository.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {

    PREPARING_SHIPMENT("배송 준비 중"),    // 상품 포장 및 배송 준비
    SHIPPED("배송 중"),                   // 배송 중
    DELIVERED("배송 완료");               // 상품 도착

    private final String description;
}
