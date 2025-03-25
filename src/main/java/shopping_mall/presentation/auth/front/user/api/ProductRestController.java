package shopping_mall.presentation.auth.front.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping_mall.application.service.ProductService;
import shopping_mall.domain.product.model.Cart;
import shopping_mall.presentation.auth.annotation.AuthUserKey;
import shopping_mall.presentation.auth.front.user.api.dto.CartReq;
import shopping_mall.presentation.auth.front.user.api.dto.CartRes;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @PostMapping("/cart")
    public void insertCart(@RequestBody CartReq cartreq, @AuthUserKey Long userId) {
        Cart cart = cartreq.toModel(userId);
        productService.insertCart(cart);
    }

    @PostMapping("/cart/fetch")
    public List<CartRes> getCartItems(@RequestBody List<Long> productIds) {
        // productId 리스트를 기반으로 상품 정보 조회
        return productService.getProductsByIds(productIds);
    }
}
