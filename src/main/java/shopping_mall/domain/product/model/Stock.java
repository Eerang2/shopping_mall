package shopping_mall.domain.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.product.entity.ProductEntity;
import shopping_mall.domain.product.entity.StockEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    private Long key;
    private int quantity;  // 양수: 입고, 음수: 출고
    private LocalDateTime createdAt;
    private Long productKey;

    // DTO → Entity 변환
    public StockEntity toEntity(ProductEntity product) {
        return StockEntity.builder()
                .quantity(quantity)
                .product(product)
                .build();
    }
}
