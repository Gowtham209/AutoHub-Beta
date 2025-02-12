package com.AutoHub.autohub_backend.SwaggerConfiguration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
 
//	@Bean
//	public OpenAPI customOpenAPI() {
//		
//		return new OpenAPI()
//				.info(new Info().title("Auto-Hub Application").version("1.0").description("Welcome to Library Management Application"))				
//				.addSecurityItem(new SecurityRequirement().addList("JavaInUseSecurityScheme"))
//				.components(new Components().addSecuritySchemes("JavaInUseSecurityScheme", new SecurityScheme()
//						.name("JavaInUseSecurityScheme").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
//		
//	}
	 @Bean
	    public GroupedOpenApi publicApi() {
	        return GroupedOpenApi.builder()
	                .group("public")
	                .pathsToMatch("/**")
	                .build();
	    }
}
 