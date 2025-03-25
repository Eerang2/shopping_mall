package shopping_mall.domain.product.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.product.entity.CartEntity;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Long key;
    private Long userId;
    private int quantity;
    private Long productId;
    private LocalDateTime createdAt;

    public CartEntity toEntity() {
        return CartEntity.builder()
                .userId(this.userId)
                .quantity(this.quantity)
                .productId(this.productId)
                .build();
    }

}
