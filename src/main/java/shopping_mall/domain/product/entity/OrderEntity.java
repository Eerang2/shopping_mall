package shopping_mall.domain.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shopping_mall.domain.auth.enums.ApprovalStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_key")
    private Long key;

    @Column(nullable = false)
    private String merchantUid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @Column(nullable = false)
    private Long productKey;

    @Column(nullable = false)
    private Long userKey;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

}
