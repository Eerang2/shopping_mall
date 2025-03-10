package shopping_mall.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter filter) {
        this.jwtAuthenticationFilter = filter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/register", "/api/admin/login", "/api/admin/check-id").permitAll()
                        .requestMatchers("/admin/register", "/admin/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/api/admin/**", "admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll() // 모든 요청 허용
                )
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화 (필요하면 활성화)
                .logout(logout -> logout
                        .logoutUrl("/logout") // 기본 /logout 대신 다른 URL 사용
                        .logoutSuccessUrl("/logout") // 로그아웃 성공 후 이동할 페이지
                );

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }

}
