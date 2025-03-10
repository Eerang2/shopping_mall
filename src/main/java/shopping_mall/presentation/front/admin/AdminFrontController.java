package shopping_mall.presentation.front.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping_mall.application.auth.service.impl.SellerServiceImpl;
import shopping_mall.domain.auth.entity.SellerEntity;
import shopping_mall.domain.auth.enums.Role;
import shopping_mall.domain.auth.model.Admin;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminFrontController {

    private final SellerServiceImpl sellerService;

    @GetMapping("/admin/create")
    public String createForm() {
        return "admin/createAdmin";
    }

    @GetMapping("/admin/mypage")
    public String myPageForm(Model model) {
        model.addAttribute("admin", new Admin("admin", Role.ADMIN));
        return "admin/mypage";
    }

    @GetMapping("/admin/approve-seller")
    public String approveForm(Model model) {
        List<SellerEntity> notApproveSellers = sellerService.findAll();
        System.out.println(notApproveSellers.get(0).getKey());
        model.addAttribute("sellers", notApproveSellers);
        return "admin/approve-seller";
    }
}
