package shopping_mall.infrastructure.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shopping_mall.application.service.dto.AuthUser;
import shopping_mall.domain.enums.Role;
import shopping_mall.domain.model.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private final String secretKey;
    private final SecretKey key;

    public JwtUtil(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = secretKey;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    Long EXPIRATION_TIME_MS = 1000 * 60 * 60 * 24L; // 24시간
    private static final String USER_NO_KEY_NAME = "key";
    private static final String USER_ID_KEY_NAME = "name";
    private static final String USER_ROLE_KEY_NAME = "role";

    public String createAccessToken(final AuthUser user) {
        return this.createAccessToken(user, EXPIRATION_TIME_MS);
    }

    public String createAccessToken(final AuthUser user, final long expirationTimeMs) {
        String token = Jwts.builder()
                .claim(USER_NO_KEY_NAME, user.getKey())
                .claim(USER_ID_KEY_NAME, user.getId())
                .claim(USER_ROLE_KEY_NAME, user.getRole().name()) // 역할 추가
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(key)
                .compact();
        log.debug("created token : {} ", token);
        return token;
    }

    public User getLoginUserFromAccessToken(final String accessToken) {
        Claims claims = getClaims(accessToken);

        return User.builder()
                .key(claims.get(USER_NO_KEY_NAME, Long.class))
                .id(claims.get(USER_ID_KEY_NAME, String.class))
                .role(Role.valueOf(claims.get(USER_ROLE_KEY_NAME, String.class))) // 역할 추가
                .build();
    }

    private Claims getClaims(final String accessToken) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException eje) {
            throw new IllegalArgumentException("No Token");
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Token");
        }
    }

    // 쿠키에서 토큰 가져오는 유틸리티 추가
//    public String extractTokenFromCookie(String cookieHeader) {
//        if (cookieHeader == null || cookieHeader.isEmpty()) return null;
//
//        String[] cookies = cookieHeader.split("; ");
//        for (String cookie : cookies) {
//            if (cookie.startsWith("accessToken=")) {
//                return cookie.substring("accessToken=".length());
//            }
//        }
//        return null;
//    }
}
