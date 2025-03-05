package shopping_mall.domain.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.auth.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_key")
    private Long key;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;   // 재고

    @Column(nullable = false)
    private ProductStatus status;

    @Column(name = "discount_key")
    private Long discountKey;    // 할인 정책 pk

    @Column(nullable = false, name = "seller_key")
    private Long sellerKey;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}

