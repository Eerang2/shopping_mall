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

    @GetMapping("/payment/cart/checkout")
    public String paymentForm(@RequestParam(value = "products", required = false) String products,
                              @AuthUserKey Long userKey,
                              Model model) {
        List<ProductOrderRequest> cartProducts = parseProductParam(products);
        return renderPaymentPage(cartProducts, userKey, model);
    }

    @GetMapping("/payment/direct/checkout")
    public String paymentDirectForm(@RequestParam(value = "products", required = false) String products,
                                    @AuthUserKey Long userKey,
                                    Model model) {
        List<ProductOrderRequest> directProducts = parseProductParam(products);
        return renderPaymentPage(directProducts, userKey, model);
    }

    // 🔽 공통 로직 추출
    private String renderPaymentPage(List<ProductOrderRequest> products, Long userKey, Model model) {
        CartProductRes res = cartService.findAllByCartProducts(products, userKey);
        User user = userService.findUser(userKey);
        UserShippingAddress userShippingAddress = userService.userAddress(userKey);

        boolean addressExists = (userShippingAddress != null);
        model.addAttribute("addressExists", addressExists);

        if (addressExists) {
            model.addAttribute("userShippingAddress", userShippingAddress);
        }

        model.addAttribute("user", user);
        model.addAttribute("response", res);

        return "payment/cart";
    }

    // 🔽 products 파싱 로직도 따로 빼자
    private List<ProductOrderRequest> parseProductParam(String products) {
        List<ProductOrderRequest> productList = new ArrayList<>();
        if (products != null && !products.isEmpty()) {
            String[] productInfo = products.split(",");
            for (int i = 0; i < productInfo.length; i += 2) {
                Long productId = Long.parseLong(productInfo[i]);
                int quantity = Integer.parseInt(productInfo[i + 1]);
                productList.add(new ProductOrderRequest(productId, quantity));
            }
        }
        return productList;
    }

    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return "payment/success";
    }

}
