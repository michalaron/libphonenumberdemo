package com.example.libphonenumberdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

  private static final String SECURITY_SCHEME = "BearerAuthentication";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.OAS_30)
        .select()
        .paths(PathSelectors.any()) //this is important so that non-application (like Spring actuator) endpoints are excluded
        .build()
        .apiInfo(
            new ApiInfoBuilder()
                .title("Libphonenumber test app API")
                .build()
        )
        .useDefaultResponseMessages(false);
  }


}
