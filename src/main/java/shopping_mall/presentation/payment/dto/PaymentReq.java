package shopping_mall.presentation.payment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import shopping_mall.domain.auth.enums.UserGrade;
import shopping_mall.infrastructure.config.jakson.BigDecimalDeserializer;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class PaymentReq {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Single {
        private String productName;

        @JsonDeserialize(using = BigDecimalDeserializer.class)
        private BigDecimal productPrice;

        private String merchantUid;

        @JsonDeserialize(using = BigDecimalDeserializer.class)
        private BigDecimal couponDiscount;

        private UserGrade userGrade;

        @JsonDeserialize(using = BigDecimalDeserializer.class)
        private BigDecimal gradeDiscount;

        @JsonDeserialize(using = BigDecimalDeserializer.class)
        private BigDecimal eventDiscount;

        private BigDecimal finalPrice;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Cart {
        List<CartItem> cartItems;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CartItem {
        private Long productId;
        private int quantity;
    }



}
