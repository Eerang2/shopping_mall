package shopping_mall.domain.auth.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.auth.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long key;
    private String name;
    private BigDecimal price;
    private int stock;   // 재고
    private ProductStatus status;
    private String uniqueImagePath;
    private Long discountKey;    // 할인 정책 pk
    private Long sellerKey;
    private LocalDateTime createdAt;

}
