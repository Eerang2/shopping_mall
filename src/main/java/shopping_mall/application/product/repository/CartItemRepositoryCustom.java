package shopping_mall.application.product.repository;

import shopping_mall.application.product.service.dto.CartItemDto;

import java.util.List;

public interface CartItemRepositoryCustom {
    List<CartItemDto> findCartItemsWithProduct(Long cartMainKey);
}
