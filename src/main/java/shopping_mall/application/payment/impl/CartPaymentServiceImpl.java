package shopping_mall.application.payment.impl;

import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shopping_mall.application.payment.PaymentService;
import shopping_mall.presentation.payment.dto.PaymentReq;

import java.io.IOException;
import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartPaymentServiceImpl implements PaymentService<PaymentReq.Cart> {
    @Override
    public String checkUser(Long key, BigDecimal gradeDiscountPrice) {
        return "";
    }

    @Override
    public void checkPrice(PaymentReq.Cart prices) {

    }

    @Override
    public void saveOrder(PaymentReq.Cart reqest, Long userKey) {

    }

    @Override
    public BigDecimal verifyPayment(String impUid) throws IamportResponseException, IOException {
        return null;
    }
}
