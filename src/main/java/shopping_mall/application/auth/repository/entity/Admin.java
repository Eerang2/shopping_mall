package shopping_mall.application.auth.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shopping_mall.application.auth.enums.ApprovalStatus;
import shopping_mall.application.auth.enums.Role;

import java.time.LocalDateTime;

@Entity
@Table(name = "ADMIN")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_key")
    private Long key;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;  // 관리자 생성일

    @LastModifiedDate
    private LocalDateTime passwordSetAt;  // 비밀번호 생성일

    public void updatePassword(Long key, String encodedPassword) {
        this.key = key;
        this.password = encodedPassword;
        this.passwordSetAt = LocalDateTime.now();
        this.status = ApprovalStatus.APPROVED;
    }

    public static Admin of(String uniqueId, String name, Role role) {
        return new Admin(uniqueId, name, role);
    }

    public Admin(String id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Admin(String name, Role role) {
        this.name = name;
        this.role = role;
    }
}
