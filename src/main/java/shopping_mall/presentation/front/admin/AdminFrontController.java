package shopping_mall.presentation.front.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminFrontController {

    @GetMapping("/admin/create")
    public String createForm() {
        return "admin/createAdmin";


    }
}
