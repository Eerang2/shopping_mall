package shopping_mall.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shopping_mall.domain.auth.enums.ApprovalStatus;
import shopping_mall.domain.auth.enums.Role;

import java.time.LocalDateTime;

@Entity
@Table(name = "SELLER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SellerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_key")
    private Long key;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String registrationNumber;  // 사업자 등록 번호

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
