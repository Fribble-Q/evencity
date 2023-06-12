package com.fribbleQ.evencity.filter;


import com.alibaba.fastjson.JSON;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

//@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
     public static final  AntPathMatcher pathMatcher  =new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        log.info("url {} :",uri);

        String[] strings =new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/page/login/login.html",
                "/backend/**",
                "/front/**"
        };


        boolean check = check(strings, uri);

        if (check) {
            log.info("本次请求{}不用处理", uri);
            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户id为：{}", request.getSession().getAttribute("employee"));
        if (request.getSession().getAttribute("employee")!=null) {
            // Long empId = (Long) request.getSession().getAttribute("employee");
            // BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }


    public boolean check(String [] strings,String uri){
        for (String string : strings) {
            boolean match = pathMatcher.match(string, uri);
            if (match){
                return true;
            }

        }
        return false;
    }

}
