package com.learn.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser= request.getSession().getAttribute("loginUser");

        if(loginUser==null)
        {
            request.setAttribute("msg","没有权限，请先登录！");
            System.out.println("转发到index.html！=============================");
            request.getRequestDispatcher("/").forward(request,response);
            System.out.println("loginUserw为空！ 拦截");
            return false;
        }else{

            System.out.println("loginUserw不为空！ 不拦截"+loginUser);
            return true;
        }

    }
}
