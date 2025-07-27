package mjc.capstone.joinus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000", "http://meetny.kro.kr")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true); // 중요!

        registry.addMapping("/ws/**")
                .allowedOrigins("http://localhost:3000", "http://meetny.kro.kr")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true); // 추가로 필요함
    }
}