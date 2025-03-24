package shopping_mall.presentation.payment.front;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shopping_mall.application.service.ProductService;
import shopping_mall.domain.product.model.Product;

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
}
