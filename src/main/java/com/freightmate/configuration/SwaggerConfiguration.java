package com.freightmate.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Hrishikesh.Lotekar
 * @implNote Swagger Configuration class
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * Description : Provides a way to control the endpoints exposed by Swagger.
     */
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.freightmate.controller"))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }


    /**
     * Description : contains custom information about the API:.
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("Api documentation").
                description("Freightmate Suburb Application with Document")
                .version("1.0")
                .contact(new Contact("Hrishikesh Lotekar","www.rishabhsoft.com","hrishikesh.lotekar@rishabhsoft.com")).build();
    }
}
