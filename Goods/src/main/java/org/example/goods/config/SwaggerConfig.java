package org.example.goods.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Конфигерация сваггера
 */
@SecurityScheme(type = SecuritySchemeType.HTTP,
        name = "jwtAuth",
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Internet shop Api")
                        .description("This is the Goods Microservice API")
                        .contact(new Contact()
                                .name("Tribushko Danil")
                                .email("danil.tribushko@bk.ru")))
                .tags(List.of(
                        new Tag()
                                .name("Goods Controller")
                                .description("Controller for work with goods")
                ));
    }
}
