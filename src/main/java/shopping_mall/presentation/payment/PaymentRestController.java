package shopping_mall.presentation.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping_mall.application.product.service.PaymentService;
import shopping_mall.application.product.service.PortOneService;
import shopping_mall.application.product.service.dto.CheckRes;
import shopping_mall.application.product.service.dto.PaymentVerificationResponse;
import shopping_mall.presentation.auth.annotation.AuthUserKey;
import shopping_mall.presentation.payment.dto.PaymentReq;
import shopping_mall.presentation.payment.dto.PaymentRes;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentRestController {
    private final PaymentService paymentService;
    private final PortOneService portOneService;

    @PostMapping("/cart/checkout")
    public PaymentRes checkoutProduct(@RequestBody PaymentReq request,
                                      @AuthUserKey Long userKey) {

        String merchantUid = generateWithUUID();
        CheckRes checkRes = paymentService.checkout(request, userKey);

        paymentService.insertOrder(checkRes, request, userKey, merchantUid);
        return PaymentRes.of(checkRes.getAmount(), checkRes.getUserName(), merchantUid, checkRes.getProductName());
    }

    // UUID 기반 랜덤 고유값 생성
    public String generateWithUUID() {
        return "GC_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    @DeleteMapping("/cancel/{merchantUid}")
    public void checkoutProduct(@PathVariable("merchantUid") String merchantUid) {

        paymentService.cancelOrder(merchantUid);
    }

    @PostMapping("/verifyImport/{impUid}")
    public ResponseEntity<BigDecimal> verifyPayment(@PathVariable("impUid") String impUid) {
        // 1️⃣ 포트원에서 실제 결제 정보 가져오기
        PaymentVerificationResponse paymentInfo = portOneService.getPaymentInfo(impUid);

        // 2️⃣ DB에서 주문 정보 조회
        BigDecimal expectedAmount = paymentService.getOrderPriceByMerchantUid(paymentInfo.getMerchantUid());

        // 3️⃣ 결제 금액 검증
        if (paymentInfo.getAmount().compareTo(expectedAmount) == 0) {
            paymentService.updateOrderStatus(paymentInfo.getMerchantUid());
            return ResponseEntity.ok(paymentInfo.getAmount());
        } else {
            return ResponseEntity.badRequest().body(BigDecimal.ZERO);
        }
    }
}
