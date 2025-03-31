package shopping_mall.presentation.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shopping_mall.application.product.repository.entity.Product;
import shopping_mall.application.product.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public String listForm(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/list";
    }

    @GetMapping("/detail/{productId}")
    public String detailForm(@PathVariable("productId") Long productId, Model model) {
        Product products = productService.findById(productId);
        model.addAttribute("product", products);
        return "product/detail";
    }

    @GetMapping("/cart")
    public String cartForm() {
        return "product/cart";
    }

}
