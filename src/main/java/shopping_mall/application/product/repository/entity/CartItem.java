package shopping_mall.application.product.repository.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_key")
    private Long key;

    @Column(nullable = false)
    private Long productKey;

    public CartItem(Long key, Long productKey, int quantity, Long cartMainKey) {
        this.key = key;
        this.productKey = productKey;
        this.quantity = quantity;
        this.cartMainKey = cartMainKey;
    }

    @Column(nullable = false)
    private Long cartMainKey;

    @Column(nullable = false)
    private int quantity;

    private boolean ordered;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }


}
