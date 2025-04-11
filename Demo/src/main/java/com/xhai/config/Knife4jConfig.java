package com.xhai.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("需求管理API文档")
                        .version("1.0")
                        .description("需求管理系统的API文档")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
} 