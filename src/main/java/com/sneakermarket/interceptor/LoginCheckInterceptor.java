package com.sneakermarket.interceptor;

import com.sneakermarket.config.SessionConstants;
import com.sneakermarket.domain.member.MemberDto;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1. 세션에서 회원 정보 조회
        HttpSession session = request.getSession();
        MemberDto.FindForm loginMember = (MemberDto.FindForm) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        //2. 회원 정보 체크
        if(loginMember == null){
            response.sendRedirect("/login.do");
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
