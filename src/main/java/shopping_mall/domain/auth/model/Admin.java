package shopping_mall.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private LocalDateTime createdAt;
}
