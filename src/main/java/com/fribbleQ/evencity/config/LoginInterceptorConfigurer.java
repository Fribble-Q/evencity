package com.fribbleQ.evencity.config;

import com.fribbleQ.evencity.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginInterceptorConfigurer extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("/employee/login");
        patterns.add("/employee/logout");
        patterns.add("/backend/page/login/login.html");
        patterns.add("/backend/**");
        patterns.add("/front/**");

        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //1、添加资源处理器路径 即每次访问静态资源都得添加"/static/",例如localhost:8080/static/j1.jpg
        //若registry.addResourceHandler("/s/**") 则必须访问localhost:8080/s/j1.jpg
        registry.addResourceHandler("/static/**")
                //2、添加了资源处理器路径后对应的映射资源路径
                .addResourceLocations("classpath:/static/");
    }

}
