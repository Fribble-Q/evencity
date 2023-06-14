package com.fribbleQ.evencity.interceptor;

import com.alibaba.fastjson.JSON;
import com.fribbleQ.evencity.common.R;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute("employee") != null) {
            return true;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
    }
}
