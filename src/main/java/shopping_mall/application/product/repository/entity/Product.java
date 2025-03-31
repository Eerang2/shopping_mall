package shopping_mall.application.product.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

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
    private String uniqueImagePath;

    @Column(nullable = false, name = "seller_key")
    private Long sellerKey;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public static Product of(Long sellerKey, String name, BigDecimal price, int stock, String uniqueImagePath) {
        return new Product(name, price, stock, uniqueImagePath, sellerKey);
    }

    public Product(String name, BigDecimal price, int stock, String uniqueImagePath, Long sellerKey) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.uniqueImagePath = uniqueImagePath;
        this.sellerKey = sellerKey;
    }
}

