package shopping_mall.presentation.auth.front.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping_mall.application.service.ProductService;
import shopping_mall.domain.auth.annotation.AuthUserKey;
import shopping_mall.domain.product.model.Cart;
import shopping_mall.presentation.auth.front.user.api.dto.CartReq;

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
}
