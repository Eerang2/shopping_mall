package shopping_mall.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class UniqueIdGenerator {

    private final String commonPrefix;
    private final String character;
    private final int length;

    public UniqueIdGenerator(@Value("${id.prefix}") String commonPrefix,
                             @Value("${id.characters}") String character,
                             @Value("${id.length}") int length) {
        this.commonPrefix = commonPrefix;
        this.character = character;
        this.length = length;
    }

    // 고유 아이디 생성
    public String generateUniqueId() {
        String randomString = generateRandomString();
        return commonPrefix + randomString;  // 공통 접두어와 랜덤 문자열 결합
    }

    // 랜덤 문자열 생성
    private String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(character.length());
            sb.append(character.charAt(index));
        }

        return sb.toString();
    }

}
