package shopping_mall.presentation.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.application.product.repository.entity.CartItem;

@Getter
public class ProductReq {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
        private Long productKey;
        private int quantity;

        public CartItem toCartItem() {
            return CartItem.builder()
                    .productKey(this.productKey)
                    .quantity(this.quantity)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuantityUpdate {
        private Long productKey;
        private int changeQuantity;
    }
}
