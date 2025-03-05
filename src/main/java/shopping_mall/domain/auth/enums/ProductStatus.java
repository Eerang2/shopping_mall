package shopping_mall.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    SELLING("판매중"), OUT_OF_STOCK("품절"), NEARLY_OUT_OF_STOCK("품절임박");

    private final String description;
}
