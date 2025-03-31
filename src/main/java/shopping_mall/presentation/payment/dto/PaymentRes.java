package shopping_mall.presentation.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRes {

    private BigDecimal amount;
    private String userName;
    private String merchantUid;
    private String productName;

    public static PaymentRes of(BigDecimal amount, String userName, String merchantUid, String productName) {
        return new PaymentRes(amount, userName, merchantUid, productName);
    }
}
