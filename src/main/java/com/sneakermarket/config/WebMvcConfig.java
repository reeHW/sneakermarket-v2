package com.sneakermarket.config;

import com.sneakermarket.interceptor.LoggerInterceptor;
import com.sneakermarket.config.auth.LoggedInMemberArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoggedInMemberArgumentResolver loginMemberArgumentResolver;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolverList){
        resolverList.add(loginMemberArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor())
                .excludePathPatterns("/css/**", "/images/**", "/js/**");

    }

}