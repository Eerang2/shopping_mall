package shopping_mall.presentation.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shopping_mall.application.auth.repository.entity.User;
import shopping_mall.application.auth.repository.entity.UserShippingAddress;
import shopping_mall.application.auth.service.impl.UserServiceImpl;
import shopping_mall.application.product.service.CartService;
import shopping_mall.application.product.service.ProductService;
import shopping_mall.presentation.auth.annotation.AuthUserKey;
import shopping_mall.presentation.payment.dto.CartProductRes;
import shopping_mall.presentation.payment.dto.ProductOrderRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserServiceImpl userService;

    @GetMapping("/payment/cart")
    public String paymentForm(@RequestParam(value = "products", required = false) String products,
                              @AuthUserKey Long userKey,
                              Model model) {
        List<ProductOrderRequest> cartProducts = new ArrayList<>();

        // 상품아이디와 수량 형변환
        if (products != null && !products.isEmpty()) {
            String[] productInfo = products.split(",");


            for (int i = 0; i < productInfo.length; i += 2) {
                Long productId = Long.parseLong(productInfo[i]);
                int quantity = Integer.parseInt(productInfo[i + 1]);
                cartProducts.add(new ProductOrderRequest(productId, quantity));
            }
        }

        // 상품 정보 조회
        CartProductRes res = cartService.findAllByCartProducts(cartProducts, userKey);

        User user = userService.findUser(userKey);
        // 유저 배송지 정보 조회
        UserShippingAddress userShippingAddress = userService.userAddress(userKey);

        // 배송지가 존재하는지 여부 확인
        boolean addressExists = (userShippingAddress != null);
        model.addAttribute("addressExists", addressExists);

        if (addressExists) {
            model.addAttribute("userShippingAddress", userShippingAddress);
        }

        model.addAttribute("user", user);
        model.addAttribute("response", res);

        return "payment/cart";
    }

    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return "payment/success";
    }

}
