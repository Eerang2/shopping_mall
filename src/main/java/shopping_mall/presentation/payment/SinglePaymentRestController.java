package shopping_mall.presentation.payment;

import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import shopping_mall.application.payment.impl.SinglePaymentServiceImpl;
import shopping_mall.presentation.auth.annotation.AuthUserKey;
import shopping_mall.presentation.payment.dto.PaymentReq;
import shopping_mall.presentation.payment.dto.PaymentRes;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
public class SinglePaymentRestController {

    private final SinglePaymentServiceImpl paymentService;



    @PostMapping("/check")
    public ResponseEntity<PaymentRes> checkPayment(@RequestBody PaymentReq.Single paymentReq,
                                                 @AuthUserKey Long key) {
        if (key == null) {
            throw new IllegalArgumentException("로그인을 이후 사용할 수 있습니다.");
        }

        // 유저 체크
        String userId = paymentService.checkUser(key,
                paymentReq.getGradeDiscount());

        // 최종가격 체크
        paymentService.checkPrice(paymentReq);

        // 주문 저장 ( 주문 대기 상태 )
        paymentService.saveOrder(paymentReq, key);

        PaymentRes paymentRes = PaymentRes.of(paymentReq.getFinalPrice(), userId);
        return ResponseEntity.ok(paymentRes);
    }

    @DeleteMapping("/cancel/{merchantUid}")
    @Transactional
    public ResponseEntity<String> cancelOrder(@PathVariable("merchantUid") String merchantUid) {
        int deletedCount = paymentService.deleteByMerchantUid(merchantUid);
        return ResponseEntity.ok(deletedCount > 0 ? "주문 삭제 완료" : "해당 주문 없음");
    }

    @PostMapping("/verifyImport/{impUid}")
    public ResponseEntity<BigDecimal> paymentByImpUid(@PathVariable(value = "impUid") String impUid) throws IamportResponseException, IOException {
        BigDecimal amount = paymentService.verifyPayment(impUid);
        if (amount == null) {
            log.error("검증 실패");
        }

        return ResponseEntity.ok(amount);
    }

}
