package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.config;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("OpenApiConfig")
@Schema(description = "Configuração global do OpenAPI para a aplicação.")
public class OpenApiConfig {

    @Value("${api.version}")
    private String apiVersion;

    @Bean("customOpenAPI")
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API para Gerenciamento de Aluguel de Veículos")
                        .description("Solutis School Dev Trail - Nivelamento - 2024")
                        .version(apiVersion)
                        .license(new License().name("Licença").url("https://github.com/viniciusdsandrade/desafio-locadora-de-veiculos-solutis-school-dev-trail"))
                        .contact(new Contact()
                                .name("Linkedin")
                                .url("https://www.linkedin.com/in/viniciusdsandrade/")
                        )
                );
    }

    @Bean("publicApi")
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}