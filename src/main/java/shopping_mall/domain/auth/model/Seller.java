package shopping_mall.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.auth.enums.ApprovalStatus;
import shopping_mall.domain.auth.entity.SellerEntity;
import shopping_mall.domain.auth.enums.Role;
import shopping_mall.domain.auth.exception.register.MemberIdValidateException;
import shopping_mall.domain.auth.exception.register.PasswordValidateException;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    private Long key;
    private String id;
    private String storeName;
    private String password;
    private String registrationNumber;  // 사업자 등록 번호
    private Role role;
    private ApprovalStatus status;      // 관리자 승인 상태
    private LocalDateTime createdAt;

    public void validate() {
        if (!password.matches(".*\\d.*") || !password.matches(".*[A-Za-z].*")) {
            throw new PasswordValidateException("비밀번호는 숫자와 영문자, 특수문자를 포함해야합니다.");
        } else if (password.length() < 8 ) {
            throw new PasswordValidateException("비밀번호는 8자 이상입니다.");
        } else if (!id.matches("^[a-zA-Z0-9]*$")) {
            throw new MemberIdValidateException("아이디는 숫자와 영문자만 사용할수있습니다.");
        } else if (id.length() < 4 || id.length() > 12) {
            throw new MemberIdValidateException("아이디는 4자 이상 12자 이하입니다.");
        }
    }

    public static Seller of(String id, String storeName, String password, String registrationNumber) {
        return new Seller(id, storeName, password, registrationNumber, Role.SELLER, ApprovalStatus.PENDING);
    }

    public SellerEntity toEntity() {
        return SellerEntity.builder()
                .id(id)
                .storeName(storeName)
                .password(password)
                .registrationNumber(registrationNumber)
                .role(role)
                .createdAt(createdAt)
                .status(status)
                .build();
    }
    public Seller(String id, String storeName, String password, String registrationNumber, Role role, ApprovalStatus status) {
        this.id = id;
        this.storeName = storeName;
        this.password = password;
        this.registrationNumber = registrationNumber;
        this.role = role;
        this.status = status;
    }

}
