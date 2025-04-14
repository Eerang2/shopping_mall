package shopping_mall.application.product.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shopping_mall.application.product.repository.entity.enums.OrderStatus;
import shopping_mall.application.product.service.dto.PaymentVerificationResponse;

@Service
@RequiredArgsConstructor
public class PortOneService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${portone.api.key}")  // 🔑 포트원 REST API 키
    private String apiKey;

    @Value("${portone.api.secret}") // 🔑 포트원 REST API 시크릿
    private String apiSecret;

    private String getAccessToken() {
        String url = "https://api.iamport.kr/users/getToken";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format("{\"imp_key\":\"%s\", \"imp_secret\":\"%s\"}", apiKey, apiSecret);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return root.path("response").path("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("포트원 토큰 발급 실패", e);
        }
    }

    public PaymentVerificationResponse getPaymentInfo(String impUid) {
        String accessToken = getAccessToken();
        String url = "https://api.iamport.kr/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken); // 🔑 토큰을 Authorization 헤더에 추가

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode responseData = root.path("response");

            return PaymentVerificationResponse.builder()
                    .impUid(responseData.path("imp_uid").asText())
                    .merchantUid(responseData.path("merchant_uid").asText())
                    .amount(responseData.path("amount").decimalValue()) // 🔹 BigDecimal 변환
                    .status(OrderStatus.fromString(responseData.path("status").asText())) // 🔹 Enum 변환
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("결제 정보 조회 실패", e);
        }
    }

}
