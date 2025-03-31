package shopping_mall.presentation.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping_mall.application.product.service.CartService;
import shopping_mall.presentation.auth.annotation.AuthUserKey;
import shopping_mall.presentation.cart.dto.CartRes;
import shopping_mall.presentation.product.dto.ProductReq;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class CartRestController {

    private final CartService cartService;

    @PostMapping("/cart")
    public void cart(@RequestBody ProductReq.Detail req,
                     @AuthUserKey Long userKey) {

        Long cartMainKey = cartService.getOrCreateCartMain(userKey);

        cartService.insertItems(req.toCartItem(), cartMainKey);
    }

    @PostMapping("/cart/fetch")
    public ResponseEntity<List<CartRes>> cartList(@AuthUserKey Long userKey) {

        List<CartRes> cartItems = cartService.findCartItems(userKey);
        return ResponseEntity.ok(cartItems);

    }

    @PostMapping("/cart/update")
    public void productQuantityUpdate(@RequestBody ProductReq.QuantityUpdate req,
                                      @AuthUserKey Long userKey) {
        cartService.productQuantityUpdate(req, userKey);

    }
}
