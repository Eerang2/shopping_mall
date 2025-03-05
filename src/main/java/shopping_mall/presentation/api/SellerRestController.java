package shopping_mall.presentation.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping_mall.application.service.impl.SellerServiceImpl;
import shopping_mall.application.service.impl.UserServiceImpl;
import shopping_mall.infrastructure.util.CookieUtil;
import shopping_mall.presentation.dto.SellerReq;
import shopping_mall.presentation.dto.UserReq;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class SellerRestController {

    private final SellerServiceImpl sellerService;

    @GetMapping("/check-id")
    public ResponseEntity<Map<String, Boolean>> checkId(@RequestParam String userId) {
        boolean isAvailable = sellerService.checkId(userId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid SellerReq.Register seller) {
        if (!seller.passwordMatch()) {
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }
        sellerService.register(seller.toModel());
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid SellerReq.Login seller,
                      HttpServletResponse response) {

        String token = sellerService.login(seller.toModel());
        response.addCookie(CookieUtil.createJwtCookie(token));
    }

    @PostMapping("/logout")
    public void logout(@CookieValue(value = "JWT_TOKEN", required = false) final String token,
                       HttpServletResponse response) {
        response.addCookie(CookieUtil.deleteJwtCookie(token));
    }
}
