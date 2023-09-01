package com.sneakermarket.config.auth;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;

        if(exception instanceof AuthenticationServiceException) {
            errorMessage = "죄송합니다. 시스템에 오류가 발생했습니다.";
        }
        else if(exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다.";
        }
        else if(exception instanceof UsernameNotFoundException) {
            errorMessage = "현재 존재하지 않는 계정입니다. 회원가입 진행 후 로그인 해주세요.";
        }
        else if(exception instanceof LockedException) {
            errorMessage = "현재 잠긴 계정입니다.";
        }
        else errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";

        // 에러 메시지의 한글 인코딩이 깨지는 것을 방지
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");

        setDefaultFailureUrl("/auth/login?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(request, response, exception);

    }
}
