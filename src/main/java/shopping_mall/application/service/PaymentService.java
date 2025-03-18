package shopping_mall.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping_mall.application.auth.service.exception.LoginValidException;
import shopping_mall.application.service.dto.UserWithGradeDto;
import shopping_mall.infrastructure.auth.repository.UserGradeRepository;
import shopping_mall.infrastructure.auth.repository.UserRepository;
import shopping_mall.infrastructure.auth.repository.impl.UserGradeQueryRepositoryImpl;
import shopping_mall.presentation.payment.dto.PaymentReq;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final UserGradeRepository gradeRepository;
    private final UserGradeQueryRepositoryImpl gradeQueryRepository;


    public String checkUser(Long key, String grade, BigDecimal gradeDiscountPrice) {

        UserWithGradeDto userWithGrade = gradeQueryRepository.findUserWithGrade(key)
                .orElseThrow(RuntimeException::new);
        validateGrade(userWithGrade, grade, gradeDiscountPrice);

        return userWithGrade.getId();
    }

    public void checkPrice(PaymentReq prices) {

        // final price 를 제외한 나머지 값의 차
        BigDecimal result = calcPrice(prices);

        if (prices.getFinalPrice().compareTo(result) != 0) {
            throw new IllegalArgumentException("최종 금액이 알맞지않습니다.");
        }
    }

    private void validateGrade(UserWithGradeDto userWithGrade, String grade, BigDecimal gradeDiscountPrice) {

        // 등급 확인
        if (!userWithGrade.getGradeName().name().equals(grade)) {
            throw new IllegalArgumentException("등급 불일치");
        }
        // 등급별 할인율 확인
        if (userWithGrade.getGradeDiscountPrice().compareTo(gradeDiscountPrice) != 0){
            throw new IllegalArgumentException("등급에 맞지않은 할인입니다.");
        }
    }

    private BigDecimal calcPrice(PaymentReq prices) {
        return prices.getProductPrice()
                .subtract(prices.getCouponDiscount())
                .subtract(prices.getEventDiscount())
                .subtract(prices.getGradeDiscount());
    }
}
