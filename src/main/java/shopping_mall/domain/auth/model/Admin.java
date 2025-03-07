package shopping_mall.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.auth.entity.AdminEntity;
import shopping_mall.domain.auth.enums.ApprovalStatus;
import shopping_mall.domain.auth.enums.Role;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    private Long key;
    private String id;
    private String password;
    private Role role;
    private ApprovalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime passwordSetAt;

    public static Admin of(Long key, String id, String password) {
        return new Admin(key, id, password, Role.ADMIN);
    }

    public Admin(Long key, String id, String password, Role role) {
        this.key = key;
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public AdminEntity toEntity() {
        return AdminEntity.builder()
                .key(key)
                .id(id)
                .password(password)
                .role(role)
                .status(ApprovalStatus.APPROVED)
                .createdAt(createdAt)
                .passwordSetAt(passwordSetAt)
                .build();
    }
}
