package com.example.coursesite_final.config;

import com.example.coursesite_final.history.dto.HistoryDto;
import com.example.coursesite_final.history.service.HistoryService;
import com.example.coursesite_final.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final HistoryService historyService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setUserId(authentication.getName());
        historyDto.setUserAgent(RequestUtils.getUserAgent(request));
        historyDto.setUserIp(RequestUtils.getUserIp(request));
        boolean result = historyService.add(historyDto);
        if(result){
            log.info("success");
            System.out.println("성공!");
        }
        response.sendRedirect("/");
    }
}
