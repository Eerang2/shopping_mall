package shopping_mall.application.product.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shopping_mall.application.product.repository.entity.enums.OrderStatus;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class PaymentVerificationResponse {

    private String impUid;
    private String merchantUid;
    private BigDecimal amount;
    private OrderStatus status;
}
