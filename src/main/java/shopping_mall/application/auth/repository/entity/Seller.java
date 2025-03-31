package shopping_mall.application.auth.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shopping_mall.application.auth.enums.ApprovalStatus;
import shopping_mall.application.auth.enums.Role;

import java.time.LocalDateTime;

@Entity
@Table(name = "SELLER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Seller {

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

    public static Seller of(String id, String storeName, String password, String registrationNumber) {
        return new Seller(id, storeName, password, registrationNumber);
    }

    public Seller(String id, String storeName, String password, String registrationNumber) {
        this.id = id;
        this.storeName = storeName;
        this.password = password;
        this.registrationNumber = registrationNumber;
        this.role = Role.SELLER;
        this.status = ApprovalStatus.PENDING;
    }
}
