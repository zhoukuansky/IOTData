package com.iot.util.config;

import com.iot.framework.interceptor.UserInterceptor;
import com.iot.util.authentication.CurrentUserMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurationSupport {
    /**
     * 注册UserInterceptor到Springboot中
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor()).addPathPatterns("/**")
                .excludePathPatterns
                        (
                                "/loginAndRegister/login",
                                "/loginAndRegister/register",
                                "/",
                                "/csrf",
                                "/swagger-ui.html",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/IOTData",
                                "/webjars/**"
                        );
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("classpath:/webapp/");
        super.addResourceHandlers(registry);
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    /**
     * 注册CurrentUserMethodArgumentResolver，到springboot中
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver());
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }
}