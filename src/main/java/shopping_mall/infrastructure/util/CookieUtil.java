package shopping_mall.infrastructure.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public static Cookie createJwtCookie(String token) {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 24); // 1일
        return jwtCookie;
    }

    public static Cookie deleteJwtCookie(String token) {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        return jwtCookie;
    }
}
