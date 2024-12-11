package com.project.abc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("http://localhost:8080/api/uploads/**")
                .addResourceLocations("file:D:/ICBT/Assignments/Advanced Programming/abc-restaurant-images/");
    }
}
