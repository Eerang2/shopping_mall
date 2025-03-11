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
    private String name;
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

    public Admin(String id, Role role) {
        this.id = id;
        this.role = role;
    }

    public AdminEntity toEntity() {
        return AdminEntity.builder()
                .key(key)
                .password(password)
                .status(ApprovalStatus.APPROVED)
                .passwordSetAt(passwordSetAt)
                .build();
    }

    public AdminEntity toBEntity(String id, String name, Role role) {
        return AdminEntity.builder()
                .id(id)
                .name(name)
                .role(role)
                .status(ApprovalStatus.PENDING)
                .build();
    }
}
