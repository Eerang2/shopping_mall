package shopping_mall.application.product.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private Long cartItemKey;
    private Long productKey;
    private int quantity;

    private BigDecimal price;
}
