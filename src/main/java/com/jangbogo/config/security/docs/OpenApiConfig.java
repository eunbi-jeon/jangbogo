package com.jangbogo.config.security.docs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI openAPI(@Value("OpenAPI") String appVersion) {
        Info info = new Info().title("Jangbogo API").version(appVersion)
                .description("카트왕 장보고의 API입니다.")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact().name("카트왕 장보고").url("https://name.name.name/").email("jangbogo@gmail.com"))
                .license(new License().name("Apache License Version 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0"));

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(info);
    }
}
