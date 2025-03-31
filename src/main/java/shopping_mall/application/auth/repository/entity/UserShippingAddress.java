package shopping_mall.application.auth.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER_SHIPPING_ADDRESS")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Shipping_Address_key")
    private Long key;

    private String addressName;
    private String address;
    private String addressDetail;
    private boolean isDefault;

    private Long userKey;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    public static UserShippingAddress of(String addressName, String address, String addressDetail, Long userKey) {
        return new UserShippingAddress(addressName, address, addressDetail, true, userKey);
    }

    public UserShippingAddress(String addressName, String address, String addressDetail, boolean isDefault, Long userKey) {
        this.addressName = addressName;
        this.address = address;
        this.addressDetail = addressDetail;
        this.isDefault = isDefault;
        this.userKey = userKey;
    }
}
