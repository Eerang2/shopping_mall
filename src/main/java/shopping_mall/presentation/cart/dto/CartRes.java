package shopping_mall.presentation.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping_mall.application.product.repository.entity.Product;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRes {

    private String productImage;
    private BigDecimal productPrice;
    private String productName;
    private Long productKey;
    private int quantity;

    public static CartRes of(Product product, int quantity) {
        return new CartRes(product.getUniqueImagePath(), product.getPrice(), product.getKey(), quantity, product.getName());

    }

    public CartRes(String productImage, BigDecimal productPrice, Long productKey, int quantity, String productName) {
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productName = productName;
        this.productKey = productKey;
        this.quantity = quantity;
    }
}
