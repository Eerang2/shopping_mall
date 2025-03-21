package shopping_mall.domain.policy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.auth.enums.UserGrade;

import java.math.BigDecimal;

@Entity
@Table(name = "GRADE_POLICY")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradePolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_policy_key")
    private Long key;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGrade name;        // 등급명

    @Column(nullable = false)
    private BigDecimal discountRate;    // 할인율

    @Column(nullable = false)
    private Boolean freeShipping;       // 무료 배송 여부

    @Column(nullable = false)
    private BigDecimal upgradeCondition;       // 승급 조건 금액


}
