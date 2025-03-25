package shopping_mall.presentation.auth.front.user.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@Builder
public class CartRes {

    private Long key;
    private String productName;
    private String productImage;
    private BigDecimal productPrice;

    public CartRes(Long key, String productName, String productImage, BigDecimal productPrice) {
        this.key = key;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }
}
