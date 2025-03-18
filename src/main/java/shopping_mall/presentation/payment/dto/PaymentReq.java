package shopping_mall.presentation.payment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import shopping_mall.infrastructure.config.jakson.BigDecimalDeserializer;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentReq {

    private String productName;

    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal productPrice;

    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal couponDiscount;

    private String userGrade;

    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal gradeDiscount;

    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal eventDiscount;

    private BigDecimal finalPrice;

}
