package com.example.springbootwithjwttoken.config;


import com.example.springbootwithjwttoken.common.APIResponse;
import com.example.springbootwithjwttoken.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")&&jwtUtils.validateJwt(token.substring(7))) {
            return true;
        }else {
            return false;
        }
//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
