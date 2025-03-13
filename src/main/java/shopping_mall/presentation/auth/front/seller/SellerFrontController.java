package shopping_mall.presentation.auth.front.seller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller")
public class SellerFrontController {

    @GetMapping("/home")
    public String index() {
        return "seller/index";
    }
    @GetMapping("/product/create")
    public String productCreate() {
        return "seller/product_create";
    }
}
