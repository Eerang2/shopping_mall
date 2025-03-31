package shopping_mall.presentation.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductRes {

    private List<CartProduct> products;
    private BigDecimal finalPrice;

    @Getter
    @Builder
    public static class CartProduct {
        private Long productKey;
        private String productName;
        private int quantity;
    }
}
