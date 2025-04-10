package shopping_mall.presentation.auth.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping_mall.application.auth.service.impl.UserServiceImpl;
import shopping_mall.infrastructure.util.CookieUtil;
import shopping_mall.presentation.auth.annotation.AuthUserKey;
import shopping_mall.presentation.auth.api.dto.UserReq;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceImpl userService;

    @GetMapping("/check-id")
    public ResponseEntity<Map<String, Boolean>> checkId(@RequestParam String username) {
        boolean isAvailable = userService.checkId(username);

        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid UserReq.Register user) {
        if (user.passwordMatch()) {
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }
        userService.register(user.toUser());
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid UserReq.Login user,
                         HttpServletResponse response) {

        String token = userService.login(user.toUser());
        response.addCookie(CookieUtil.createJwtCookie(token));
    }

    @PostMapping("/logout")
    public void logout(@CookieValue(value = "JWT_TOKEN", required = false) final String token,
                       HttpServletResponse response) {
        response.addCookie(CookieUtil.deleteJwtCookie(token));
    }

    @PostMapping("/user/address")
    public ResponseEntity<Boolean> address(@RequestBody UserReq.Address address, @AuthUserKey Long userKey) {
        boolean userAddress = userService.createUserAddress(userKey, address.toAddress());
        return ResponseEntity.ok(userAddress);

    }
}
