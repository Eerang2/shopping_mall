package shopping_mall.presentation.auth.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping_mall.application.auth.service.impl.AdminServiceImpl;
import shopping_mall.infrastructure.util.CookieUtil;
import shopping_mall.presentation.auth.dto.AdminReq;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminRestController {

    private final AdminServiceImpl adminService;

    @GetMapping("/check-id")
    public ResponseEntity<Map<String, Boolean>> checkId(@RequestParam String adminId) {
        boolean isAvailable = adminService.checkId(adminId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid AdminReq.Register admin) {
        if (!admin.passwordMatch()) {
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }
        adminService.register(admin.toModel());
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid AdminReq.Login admin,
                      HttpServletResponse response) {

        String token = adminService.login(admin.toModel());
        response.addCookie(CookieUtil.createJwtCookie(token));
    }

    @PostMapping("/logout")
    public void logout(@CookieValue(value = "JWT_TOKEN", required = false) final String token,
                       HttpServletResponse response) {
        response.addCookie(CookieUtil.deleteJwtCookie(token));
    }

    @PostMapping("/create")
    public void createAdmin(@RequestBody  AdminReq.Create admin) {
        adminService.createAdmin(admin.toModel());
    }
}
