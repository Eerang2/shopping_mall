package shopping_mall.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.service.dto.UserWithGradeDto;
import shopping_mall.domain.auth.enums.ApprovalStatus;
import shopping_mall.domain.product.entity.OrderEntity;
import shopping_mall.domain.product.entity.ProductEntity;
import shopping_mall.infrastructure.auth.repository.OrderRepository;
import shopping_mall.infrastructure.auth.repository.ProductRepository;
import shopping_mall.infrastructure.auth.repository.impl.UserGradeQueryRepositoryImpl;
import shopping_mall.presentation.payment.dto.PaymentReq;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final UserGradeQueryRepositoryImpl gradeQueryRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    public String checkUser(Long key, BigDecimal gradeDiscountPrice) {

        UserWithGradeDto userWithGrade = gradeQueryRepository.findUserWithGrade(key)
                .orElseThrow(RuntimeException::new);
        validateGrade(userWithGrade, gradeDiscountPrice);

        return userWithGrade.getId();
    }

    public void checkPrice(PaymentReq prices) {

        // final price 를 제외한 나머지 값의 차
        BigDecimal result = calcPrice(prices);

        if (prices.getFinalPrice().compareTo(result) != 0) {
            throw new IllegalArgumentException("최종 금액이 알맞지않습니다.");
        }
    }

    @Transactional
    public void saveOrder(PaymentReq req, Long userKey) {
        System.out.println("me : " + req.getMerchantUid());
        ProductEntity product = productRepository.findByName(req.getProductName())
                .orElseThrow(() -> new RuntimeException("일치한 상품이 없습니다."));
        System.out.println("product: " + product.getName());
        System.out.println("product key : " + product.getKey());

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

    private void validateGrade(UserWithGradeDto userWithGrade, BigDecimal gradeDiscountPrice) {
        System.out.println("validateGrade");
        // 등급별 할인율 확인
        if (userWithGrade.getGradeDiscountPrice().compareTo(gradeDiscountPrice) != 0){
            throw new IllegalArgumentException("등급에 맞지않은 할인입니다.");
        }
    }

    public int deleteByMerchantUid(String merchantUid) {
        return orderRepository.deleteByMerchantUid(merchantUid);
    }

    private BigDecimal calcPrice(PaymentReq prices) {
        return prices.getProductPrice()
                .subtract(prices.getCouponDiscount())
                .subtract(prices.getEventDiscount())
                .subtract(prices.getGradeDiscount());
    }
}
