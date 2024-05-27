package com.example.Ecommerce.config;

import com.example.Ecommerce.dto.RequestMeta;
import com.example.Ecommerce.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    private final RequestMeta requestMeta;

    public JwtInterceptor(RequestMeta requestMeta) {
        this.requestMeta = requestMeta;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("authorization");

        if (!(request.getRequestURI().contains("login") || request.getRequestURI().contains("signup"))) {
            Claims claims = jwtUtils.verify(auth);

            requestMeta.setUserName(claims.get("name").toString());
            requestMeta.setUserId(Long.valueOf(claims.getIssuer()));
            requestMeta.setUserType(claims.get("type").toString());
            requestMeta.setEmailId(claims.get("email").toString());
        }

        return true;
    }
}
