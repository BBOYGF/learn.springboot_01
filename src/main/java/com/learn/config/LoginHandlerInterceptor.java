package com.learn.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object loginUser= request.getSession().getAttribute("username");
        System.out.println("拦截一下"+loginUser);
        if(loginUser==null)
        {
            request.getRequestDispatcher("/").forward(request,response);
            return false;
        }else{
            return true;
        }

    }
}
