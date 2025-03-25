package shopping_mall.application.payment;

import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;
import java.math.BigDecimal;

public interface PaymentService<T> {

    String checkUser(Long key, BigDecimal gradeDiscountPrice);

    void checkPrice(T prices);

    void saveOrder(T reqest, Long userKey);

    BigDecimal verifyPayment(String impUid) throws IamportResponseException, IOException;
}
