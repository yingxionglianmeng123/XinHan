package com.xhai.aspect;

import com.xhai.annotation.RequireToken;
import com.xhai.service.WxLoginService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class TokenAspect {

    @Autowired
    private WxLoginService wxLoginService;

    @Around("@annotation(requireToken)")
    public Object around(ProceedingJoinPoint point, RequireToken requireToken) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException("请求上下文为空");
        }

        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            throw new RuntimeException("未提供token");
        }

        // 处理Bearer token格式
        String token;
        if (authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // 移除"Bearer "前缀
        } else {
            token = authHeader;
        }

        String userId = wxLoginService.verifyToken(token);
        if (userId == null) {
            throw new RuntimeException("token无效或已过期");
        }

        // 将用户ID存入请求属性中
        request.setAttribute("currentUserId", userId);

        return point.proceed();
    }
} 