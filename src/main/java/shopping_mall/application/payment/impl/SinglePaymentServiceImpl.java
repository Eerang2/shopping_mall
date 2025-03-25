package shopping_mall.application.payment.impl;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.payment.PaymentService;
import shopping_mall.application.service.ImapotClient;
import shopping_mall.application.service.dto.UserWithGradeDto;
import shopping_mall.domain.auth.enums.ApprovalStatus;
import shopping_mall.domain.product.entity.OrderEntity;
import shopping_mall.domain.product.entity.ProductEntity;
import shopping_mall.infrastructure.auth.repository.OrderRepository;
import shopping_mall.infrastructure.auth.repository.ProductRepository;
import shopping_mall.infrastructure.auth.repository.impl.UserGradeQueryRepositoryImpl;
import shopping_mall.presentation.payment.dto.PaymentReq;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class SinglePaymentServiceImpl implements PaymentService<PaymentReq.Single> {

    private final UserGradeQueryRepositoryImpl gradeQueryRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ImapotClient imapotClient;

    @Override
    public String checkUser(Long key, BigDecimal gradeDiscountPrice) {

        UserWithGradeDto userWithGrade = gradeQueryRepository.findUserWithGrade(key)
                .orElseThrow(RuntimeException::new);

        if (userWithGrade.getGradeDiscountPrice().compareTo(gradeDiscountPrice) != 0) {
            throw new IllegalArgumentException("등급에 맞지않은 할인입니다.");
        }

        return userWithGrade.getId();
    }

    @Override
    public void checkPrice(PaymentReq.Single prices) {

        // final price 를 제외한 나머지 값의 차
        BigDecimal result = calcPrice(prices);

        if (prices.getFinalPrice().compareTo(result) != 0) {
            throw new IllegalArgumentException("최종 금액이 알맞지않습니다.");
        }
    }

    @Override
    @Transactional
    public void saveOrder(PaymentReq.Single req, Long userKey) {
        ProductEntity product = productRepository.findByName(req.getProductName())
                .orElseThrow(() -> new RuntimeException("일치한 상품이 없습니다."));

        OrderEntity entity = OrderEntity.builder()
                .merchantUid(req.getMerchantUid())
                .productKey(product.getKey())
                .userKey(userKey)
                .status(ApprovalStatus.PENDING)
                .build();
        orderRepository.save(entity);
    }

    // 매일 새벽 3시에 실행 (cron 표현식: "0 0 3 * * *")
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void deleteExpiredOrders() {
        LocalDateTime expirationTime = LocalDateTime.now().minusDays(1); // 24시간 지난 주문만 삭제
        int deletedCount = orderRepository.deleteByStatusAndCreatedAtBefore(ApprovalStatus.PENDING, ApprovalStatus.REJECTED, expirationTime);
        log.info("delete count : {}", deletedCount);
    }

    @Override
    @Transactional
    public BigDecimal verifyPayment(String impUid) throws IamportResponseException, IOException {
        // impUid로 결제 검증을 요청하고 응답을 받음
        IamportResponse<Payment> response = imapotClient.verifyPayment(impUid);

        // 응답 코드가 "00"이면 결제 검증이 성공한 것
        if (response.getCode() == 0) {
            // 검증 성공 시 주문 상태 업데이트
            orderRepository.updateStateApprove(response.getResponse().getMerchantUid(), ApprovalStatus.APPROVED);
            return response.getResponse().getAmount();
        } else {
            return null;
        }
    }

    public int deleteByMerchantUid(String merchantUid) {
        return orderRepository.deleteByMerchantUid(merchantUid);
    }

    private BigDecimal calcPrice(PaymentReq.Single prices) {
        return prices.getProductPrice()
                .subtract(prices.getCouponDiscount())
                .subtract(prices.getEventDiscount())
                .subtract(prices.getGradeDiscount());
    }
}
