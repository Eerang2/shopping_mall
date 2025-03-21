package shopping_mall.presentation.payment.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentRes {

    private boolean success;
    private BigDecimal finalPrice;
    private String userName;

    public static PaymentRes of(BigDecimal finalPrice, String userName) {
        return new PaymentRes(true, finalPrice, userName);
    }
}
