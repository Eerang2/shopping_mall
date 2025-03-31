package shopping_mall.presentation.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductOrderRequest {
    private Long productKey;
    private int quantity;
}
