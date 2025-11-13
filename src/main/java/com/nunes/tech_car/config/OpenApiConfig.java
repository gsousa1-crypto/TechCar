package com.nunes.tech_car.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI techCarOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TechCar API")
                        .description("API para sistema de venda de veículos")
                        .version("v1.0.0")
                        .termsOfService("http://techcar.com/terms")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Suporte TechCar")
                                .email("suporte@techcar.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}