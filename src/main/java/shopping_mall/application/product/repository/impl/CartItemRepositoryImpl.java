package shopping_mall.application.product.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shopping_mall.application.product.repository.CartItemRepositoryCustom;
import shopping_mall.application.product.repository.entity.QCartItem;
import shopping_mall.application.product.repository.entity.QProduct;
import shopping_mall.application.product.service.dto.CartItemDto;

import java.util.List;

@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<CartItemDto> findCartItemsWithProduct(Long cartMainKey) {

        QCartItem cartItem = QCartItem.cartItem;
        QProduct product = QProduct.product;

        return queryFactory
                .select(Projections.constructor(CartItemDto.class,
                        cartItem.key,
                        cartItem.productKey,
                        cartItem.quantity,
                        product.price
                ))
                .from(cartItem)
                .join(product).on(cartItem.productKey.eq(product.key))
                .where(cartItem.cartMainKey.eq(cartMainKey))
                .fetch();
    }
}
