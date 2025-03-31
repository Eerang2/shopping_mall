package shopping_mall.presentation.payment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.infrastructure.config.jakson.BigDecimalDeserializer;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReq {

    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal totalPrice;
    private List<Products> products;

    @Getter
    public static class Products {
        private Long productKey;
        private int quantity;
    }
}
