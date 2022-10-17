package com.example.coursesite_final.member.controller;

import com.example.coursesite_final.member.dto.FindPasswordDto;
import com.example.coursesite_final.member.service.UserSettingsService;
import com.example.coursesite_final.member.validator.findPasswordValidatorInher.CheckUserInfoMatchValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FindPasswordController {

    private final UserSettingsService userSettingsService;
    private final CheckUserInfoMatchValidator userInfoMatchValidator;

    @InitBinder // 특정 컨트롤러애서 바인딩 또는 검증 설정을 변경할 때 사용
    public void validatorBinder(WebDataBinder webDataBinder) {
        // addValidators 메서드를 사용하여 Validator 등록 > 해당 컨트롤러에서는 자동 적용
        webDataBinder.addValidators(userInfoMatchValidator);
    }

    @GetMapping("/member/find-password") // 임시 비밀번호 발급을 위한 본인 계정 확인 절차 페이지
    public String findPassword() {
        return "member/find-password";
    }

    @PostMapping("/member/find-password")
    public String findPasswordSubmit(
            @Valid FindPasswordDto findPasswordDto,
            Errors errors, Model model) {
        if(errors.hasErrors()) {
            Map<String, String> validateMap = userSettingsService.validateHandling(errors);
            for (String key : validateMap.keySet()) {
                model.addAttribute(key, validateMap.get(key));
            }
            return "member/find-password";
        }
        userSettingsService.sendResetPasswordKey(findPasswordDto);
        return "member/send-resetkey-success";
    }
}
