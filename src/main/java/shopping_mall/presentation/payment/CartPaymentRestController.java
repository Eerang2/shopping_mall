package shopping_mall.presentation.payment;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping_mall.application.payment.impl.CartPaymentServiceImpl;
import shopping_mall.presentation.auth.annotation.AuthUserKey;
import shopping_mall.presentation.payment.dto.PaymentReq;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
public class CartPaymentRestController {

    private final CartPaymentServiceImpl paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody PaymentReq.Cart orderRequest, @AuthUserKey Long userId) {
        try {
//            Long orderId = paymentService.checkUser(userId, orderRequest);
//            return ResponseEntity.ok(Map.of("orderId", orderId));
            return null;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

}
