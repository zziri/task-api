package com.zziri.task.config.security;

import com.zziri.task.exception.custom.AuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger("io.ojw.mall.interceptor.JwtInterceptor");

    private static final String TOKEN = "task-authentication";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token = request.getHeader(TOKEN);

        logger.debug("JwtInterceptor > preHandle > token: " + token);

        if (request.getMethod().equals("OPTIONS")) {
            logger.debug("if request options method is options, return true");
            return true;
        }

        if (token != null && jwtTokenProvider.validateToken(token)) {
            return true;
        } else {
            throw new AuthenticationEntryPointException();
        }
    }
}
