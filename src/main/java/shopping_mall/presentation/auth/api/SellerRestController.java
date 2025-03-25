package shopping_mall.presentation.auth.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopping_mall.application.auth.service.impl.SellerServiceImpl;
import shopping_mall.application.service.FileUploadService;
import shopping_mall.infrastructure.util.CookieUtil;
import shopping_mall.presentation.auth.annotation.AuthUserKey;
import shopping_mall.presentation.auth.api.dto.SellerReq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class SellerRestController {

    private final SellerServiceImpl sellerService;
    private final FileUploadService fileUploadService;

    @GetMapping("/check-id")
    public ResponseEntity<Map<String, Boolean>> checkId(@RequestParam String sellerId) {
        boolean isAvailable = sellerService.checkId(sellerId);

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
    public ResponseEntity<String> login(@RequestBody @Valid SellerReq.Login seller,
                      HttpServletResponse response) {

        String token = sellerService.login(seller.toModel());
        response.addCookie(CookieUtil.createJwtCookie(token));
        return ResponseEntity.ok("login");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue(value = "JWT_TOKEN", required = false) final String token,
                       HttpServletResponse response) {
        // 서버 세션 무효화
        response.addCookie(CookieUtil.deleteJwtCookie(token));

        // 클라이언트 저장소(localStorage 등) 초기화 권장
        return ResponseEntity.ok("로그아웃 되었습니다. 클라이언트 저장소도 초기화해주세요.");
    }

    @PostMapping("/image-process")
    public ResponseEntity<?> imageProcess(@RequestParam("image") MultipartFile image) throws IOException {
        // 이미지 이름 암호화
        String uniqueImageName = fileUploadService.uploadImage(image);

        // 이미지 response
        Map<String, String> response = new HashMap<>();
        response.put("processedImage", uniqueImageName);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save-product")
    public ResponseEntity<String> productCreate(@RequestBody SellerReq.ProductDto product,
                              @AuthUserKey Long key) {

        sellerService.createProduct(key, product.toProduct(), product.toStock());
        return ResponseEntity.ok("success");
    }
}
