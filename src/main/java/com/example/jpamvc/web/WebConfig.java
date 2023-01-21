package com.example.jpamvc.web;

import com.example.jpamvc.web.argumentResolver.LoginMemberArgumentResolver;
import com.example.jpamvc.web.interceptor.LogInterceptor;
import com.example.jpamvc.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/",
                                     "/asset/**",
                                     "/js/**",
                                     "/css/**",
                                     "/*.ico",
                                     "/login",
                                     "/logout",
                                     "/error",
                                     "/signup")
                .excludePathPatterns(
                        "/post/{tmp:[0-9]+}"
                ).excludePathPatterns(
                        "/profile/{}"
                )

        ;
    }
}
