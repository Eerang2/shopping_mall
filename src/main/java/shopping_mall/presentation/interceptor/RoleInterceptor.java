package shopping_mall.presentation.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long key = (Long) request.getAttribute("key");

        // ✅ userKey가 없는 경우 -> 보호된 경로 접근 차단
        if (key == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized Access");
            return false;
        }
        return true;
    }
}
