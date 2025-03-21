package shopping_mall.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping_mall.presentation.interceptor.AuthUserKeyArgumentResolver;
import shopping_mall.presentation.interceptor.RoleInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthUserKeyArgumentResolver());
    }

    @Override
    public void extendMessageConverters(java.util.List<HttpMessageConverter<?>> converters) {
        // 기존 StringHttpMessageConverter의 기본 인코딩을 UTF-8로 설정
        converters.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).favorParameter(false).ignoreAcceptHeader(false);
    }
}


