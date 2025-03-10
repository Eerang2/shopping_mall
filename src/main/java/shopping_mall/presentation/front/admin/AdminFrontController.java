package shopping_mall.presentation.front.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping_mall.domain.auth.enums.Role;
import shopping_mall.domain.auth.model.Admin;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminFrontController {

    @GetMapping("/admin/create")
    public String createForm() {
        return "admin/createAdmin";
    }

    @GetMapping("/admin/mypage")
    public String myPageForm(Model model) {
        model.addAttribute("admin", new Admin("admin", Role.ADMIN));
        return "admin/mypage";
    }
}
