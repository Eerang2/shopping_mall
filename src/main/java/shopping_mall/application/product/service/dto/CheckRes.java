package shopping_mall.application.product.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CheckRes {

    private String productName;
    private BigDecimal amount;
    private String userName;

    public static CheckRes of(String productName, BigDecimal amount, String userName) {
        return new CheckRes(productName, amount, userName);
    }
}
