package com.example.newsfeed.global.interceptor;

import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.global.exception.custom.UnauthorizedException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.example.newsfeed.global.exception.ErrorCode.LOGIN_REQUIRED;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // swagger 얼리리턴
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/swagger-ui/") || requestURI.contains("/v3/api-docs")) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (isLoginRequired(handlerMethod)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("member") == null) {
                throw
            }
            SessionMemberDto loginMember = (SessionMemberDto) session.getAttribute("member");
            log.info("로그인한 사용자(id, userName, nickName, email) = {}, {}, {}, {}",
                    loginMember.getId(),
                    loginMember.getUsername(),
                    loginMember.getNickname(),
                    loginMember.getEmail()
            );
            return true;
        }
        return true;
    }

    private boolean isLoginRequired(HandlerMethod handlerMethod) {
        if (handlerMethod != null) {
            return handlerMethod.hasMethodAnnotation(LoginRequired.class);
        }
        return false;
    }
}