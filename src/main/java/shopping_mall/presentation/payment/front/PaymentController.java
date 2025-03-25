package shopping_mall.presentation.payment.front;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shopping_mall.application.service.ProductService;
import shopping_mall.application.service.dto.ProductWithQuantity;
import shopping_mall.domain.product.model.Product;
import shopping_mall.presentation.payment.dto.PaymentReq;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final ProductService productService;

    @GetMapping("/payment/{productId}")
    public String payment(@PathVariable("productId") Long productId,
                          Model model) {

        Product productById = productService.findProductById(productId);
        model.addAttribute("product", productById);

        return "payment";
    }

    @GetMapping("/payment/cart")
    public String paymentCart(@RequestParam("products") String products,
                              Model model) {

        List<PaymentReq.CartItem> cartProducts = new ArrayList<>();
        String[] productInfo = products.split(",");

        for (int i = 0; i < productInfo.length; i += 2) {
            Long productId = Long.parseLong(productInfo[i]);
            int quantity = Integer.parseInt(productInfo[i + 1]);
            System.out.println("productId: " + productId + ", quantity: " + quantity);
            cartProducts.add(new PaymentReq.CartItem(productId, quantity));
        }

        List<ProductWithQuantity> productList = productService.findAllByProducts(cartProducts);
        model.addAttribute("products", productList);

        return "cartPayment";
    }
}
