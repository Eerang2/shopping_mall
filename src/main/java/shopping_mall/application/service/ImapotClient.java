package shopping_mall.application.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImapotClient {


    private final IamportClient iamportClient;

    public ImapotClient(@Value("${import.api.key}") String apiKey, @Value("${import.secret.api.key}") String apiSecretKey) {
        iamportClient = new IamportClient(apiKey, apiSecretKey);
    }

    public IamportResponse<Payment> verifyPayment(String impUid) throws IamportResponseException, IOException {
        // impUid로 결제 검증 요청
        IamportResponse<Payment> response = iamportClient.paymentByImpUid(impUid);
        Payment payment = response.getResponse();

        if (payment != null) {
            if (!"paid".equals(payment.getStatus())) {
                throw new IllegalArgumentException("Payment is not paid.");
            }
        }

        return response;
    }
}
