package shopping_mall.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.domain.model.Seller;
import shopping_mall.domain.model.User;

@Getter
public class SellerReq {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Register {

        private String sellerId;
        private String storeName;
        private String password;
        private String confirmPassword;
        private String registrationNumber;

        public boolean passwordMatch() {
            return password.equals(confirmPassword);
        }

        public Seller toModel() {
            return Seller.builder()
                    .id(sellerId)
                    .password(password)
                    .registrationNumber(registrationNumber)
                    .storeName(storeName)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {

        private String sellerId;
        private String password;

        public Seller toModel() {
            return Seller.builder()
                    .id(sellerId)
                    .password(password)
                    .build();
        }
    }
}
