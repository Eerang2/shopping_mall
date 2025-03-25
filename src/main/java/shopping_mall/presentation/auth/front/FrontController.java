package shopping_mall.presentation.auth.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/seller/register")
    public String sellerRegister() {
        return "seller/register";
    }
    @GetMapping("/seller/login")
    public String sellerLogin() {
        return "seller/login";
    }
    @GetMapping("/admin/register")
    public String adminRegister() {
        return "admin/register";
    }
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/login";
    }

    @GetMapping("/seller/index")
    public String sellerIndex() {
        return "seller/index";
    }


}
