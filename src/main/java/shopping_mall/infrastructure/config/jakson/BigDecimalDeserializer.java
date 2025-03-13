package shopping_mall.infrastructure.config.jakson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser parser,  DeserializationContext context) throws IOException {
        String value = parser.getValueAsString();
        try {
            return new BigDecimal(value);  // 문자열을 BigDecimal로 변환
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;  // 변환 실패 시 기본값 처리
        }
    }
}
