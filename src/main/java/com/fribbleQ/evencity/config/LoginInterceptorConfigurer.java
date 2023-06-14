package com.fribbleQ.evencity.config;

import com.fribbleQ.evencity.common.JacksonObjectMapper;
import com.fribbleQ.evencity.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = new ArrayList<>();
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

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换器
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置具体的对象映射器
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        // 通过设置索引，让自己的转换器放在最前面，否则默认的jackson转换器会在前面，用不上我们设置的转换器
        converters.add(0,messageConverter);

    }
}
