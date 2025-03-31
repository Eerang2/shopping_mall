package shopping_mall.presentation.auth.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.application.auth.repository.entity.Seller;
import shopping_mall.application.product.repository.entity.Product;
import shopping_mall.infrastructure.config.jakson.BigDecimalDeserializer;

import java.math.BigDecimal;

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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDto {
        private String productName;

        @JsonDeserialize(using = BigDecimalDeserializer.class)
        private BigDecimal price;
        private int stock;
        private String image;

        public Product toModel() {
            return Product.builder()
                    .name(productName)
                    .price(price)
                    .stock(stock)
                    .uniqueImagePath(image)
                    .build();
        }
    }
}
