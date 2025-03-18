package shopping_mall.presentation.payment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping_mall.application.service.PaymentService;
import shopping_mall.domain.auth.annotation.AuthUserKey;
import shopping_mall.infrastructure.config.jakson.BigDecimalDeserializer;
import shopping_mall.presentation.payment.dto.PaymentReq;
import shopping_mall.presentation.payment.dto.PaymentRes;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentRestController {

    private final PaymentService paymentService;



    @PostMapping("/check")
    public ResponseEntity<PaymentRes> checkPayment(@RequestBody PaymentReq paymentReq,
                                                 @AuthUserKey Long key) {
        if (key == null) {
            throw new IllegalArgumentException("로그인을 이후 사용할 수 있습니다.");
        }

        // 유저 체크
        String userId = paymentService.checkUser(key,
                paymentReq.getUserGrade(),
                paymentReq.getGradeDiscount());

        // 최종가격 체크
        paymentService.checkPrice(paymentReq);

        PaymentRes paymentRes = PaymentRes.of(paymentReq.getFinalPrice(), userId);
        return ResponseEntity.ok(paymentRes);
    }


}
