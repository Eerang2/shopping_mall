package shopping_mall.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.enums.ApprovalStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "SELLER")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_key")
    private Long key;
    private String id;
    private String storeName;
    private String password;
    private String registrationNumber;  // 사업자 등록 번호
    private ApprovalStatus status;
    private LocalDateTime createdAt;

}
