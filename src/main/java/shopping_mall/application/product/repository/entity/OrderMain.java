package shopping_mall.application.product.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shopping_mall.application.product.repository.entity.enums.DeliveryStatus;
import shopping_mall.application.product.repository.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDER_MAIN")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_main_key")
    private Long key;

    @Column(nullable = false)
    private Long cartMainKey;

    @Column(nullable = false)
    private Long userKey;

    @Column(nullable = false)
    private String merchantUid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private BigDecimal totalPrice;


}
