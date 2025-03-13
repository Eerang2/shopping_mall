package shopping_mall.domain.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.auth.enums.ProductStatus;
import shopping_mall.domain.product.entity.ProductEntity;

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

    public static Product of(Long sellerKey, String name, BigDecimal price, int stock, String uniqueImagePath) {
        return new Product(name, price, stock, ProductStatus.SELLING, uniqueImagePath, sellerKey);
    }

    public ProductEntity toEntity() {
        return ProductEntity.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .status(status)
                .uniqueImagePath(uniqueImagePath)
                .sellerKey(sellerKey)
                .build();
    }

    public Product(String name, BigDecimal price, int stock, ProductStatus status, String uniqueImagePath, Long sellerKey) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.uniqueImagePath = uniqueImagePath;
        this.sellerKey = sellerKey;
    }
}
