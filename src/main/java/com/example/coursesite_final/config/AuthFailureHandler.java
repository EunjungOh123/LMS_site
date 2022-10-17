package com.example.coursesite_final.config;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

// 로그인 실패 시 처리를 담당할 handler

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;
        System.out.println("로그인 실패");

        if(exception instanceof UsernameNotFoundException){
            errorMessage = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
        } else if(exception instanceof BadCredentialsException) {
            errorMessage = "비밀번호가 맞지 않습니다.";
        } else if(exception instanceof DisabledException) {
            errorMessage = "계정이 비활성화 되었습니다. 관리자에게 문의하세요.";
        } else if(exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else if(exception instanceof InternalAuthenticationServiceException) {
//            errorMessage = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
            errorMessage = exception.getMessage();
        } else {
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8"); // 한글 인코딩 깨지지 않도록 함

        setUseForward(true);
        setDefaultFailureUrl("/member/login?error=true&exception="+errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}
