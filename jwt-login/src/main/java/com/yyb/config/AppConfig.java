package com.yyb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
@Configuration
public class AppConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration rg = registry.addInterceptor(new AuthFilter());
        rg.addPathPatterns("/**");
        rg.excludePathPatterns("/user/**");
        rg.excludePathPatterns("/home/index");
        rg.excludePathPatterns("/error");
    }
}
