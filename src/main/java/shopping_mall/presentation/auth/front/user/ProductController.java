package shopping_mall.presentation.auth.front.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shopping_mall.application.service.ProductService;
import shopping_mall.domain.product.model.Product;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public String products(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "/product/products";
    }

    @GetMapping("/detail/{productId}")
    public String productDetail(Model model,
                                @PathVariable("productId") Long productId) {
        Product products = productService.findProductById(productId);
        model.addAttribute("product", products);
        return "/product/detail";
    }
}
