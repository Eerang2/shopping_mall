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

    public Admin(String id, Role role) {
        this.id = id;
        this.role = role;
    }

    public AdminEntity toEntity(String id, String name, Role role) {
        return AdminEntity.builder()
                .id(id)
                .name(name)
                .role(role)
                .status(ApprovalStatus.PENDING)
                .build();
    }
}
