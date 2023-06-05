package com.vp.voicepocket.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI demoOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Voice Pocket project API")
                        .description("Voice Pocket application")
                        .version("v0.0.1"));
    }
}
