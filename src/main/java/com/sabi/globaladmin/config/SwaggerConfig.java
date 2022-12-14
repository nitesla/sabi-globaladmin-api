package com.sabi.globaladmin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()

                .apis(RequestHandlerSelectors.basePackage("com.sabi.globaladmin.controller"))
                .paths(regex("/*.*")).build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("Sabi-GLOBAL-ADMIN Nig LTD")
                .description("Sabi global admin application").version("1.0.0")
                .license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Sabi Nig LTD", "www.xxxxxxx.com ", "info@xxxxxx.com")).build();
    }







}

