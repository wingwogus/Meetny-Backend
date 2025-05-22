package mjc.capstone.joinus.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("1.0.0") //버전
                .title("RamenLog API") //이름
                .description("라멘로그 API"); //설명
        return new OpenAPI()
                .info(info);
    }
}