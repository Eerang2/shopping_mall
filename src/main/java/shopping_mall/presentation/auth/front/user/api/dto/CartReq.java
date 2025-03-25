package shopping_mall.presentation.auth.front.user.api.dto;

import lombok.Builder;
import lombok.Getter;
import shopping_mall.domain.product.model.Cart;

@Getter
@Builder
public class CartReq {
    private Long productId;
    private int quantity;

    public Cart toModel(Long userId) {
        return Cart.builder()
                .productId(productId)
                .quantity(quantity)
                .userId(userId)
                .build();
    }
}
