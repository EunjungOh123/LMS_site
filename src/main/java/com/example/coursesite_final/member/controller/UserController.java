package com.example.coursesite_final.member.controller;

import com.example.coursesite_final.member.dto.UserRegisterDto;
import com.example.coursesite_final.member.service.UserService;
import com.example.coursesite_final.member.validator.registerValidatorInher.CheckEmailValidator;
import com.example.coursesite_final.member.validator.registerValidatorInher.CheckNicknameValidator;
import com.example.coursesite_final.member.validator.registerValidatorInher.CheckPasswordMatchValidator;
import com.example.coursesite_final.member.validator.registerValidatorInher.CheckUserIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

/* 회원 가입 , 이메일 인증 , 로그인 */

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final CheckUserIdValidator checkUserIdValidator;
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckEmailValidator checkEmailValidator;
    private final CheckPasswordMatchValidator checkPasswordMatchValidator;

    // 중복 체크를 위한 커스텀 유효성 검사
    @InitBinder // 특정 컨트롤러애서 바인딩 또는 검증 설정을 변경할 때 사용
    public void validatorBinder(WebDataBinder webDataBinder) {
        // addValidators 메서드를 사용하여 Validator 등록 > 해당 컨트롤러에서는 자동 적용
        webDataBinder.addValidators(checkUserIdValidator);
        webDataBinder.addValidators(checkNicknameValidator);
        webDataBinder.addValidators(checkEmailValidator);
        webDataBinder.addValidators(checkPasswordMatchValidator);
    }

    /* 컨트롤러가 요청 올 때마다 WebDataBinder 호출되면서 WebDataBinder 에 등록한 검증기가 매번 적용 */

    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(@Valid UserRegisterDto registerDto,
                                 Errors errors, Model model) {
        /* post 요청시 넘어온 user 입력값에서 Validation에 걸리는 경우 */
        if (errors.hasErrors()) {
            /* 회원가입 실패시 입력 데이터 유지 */
            model.addAttribute("registerDto", registerDto);
            /* 유효성 검사를 통과하지 못한 필드와 message 모델에 매핑해서 뷰로 전달 */
            Map<String, String> validateMap = userService.validateHandling(errors);
            // map.keySet() -> 모든 key 값 가져온다
            // 가져온 키로 반복문을 통해 키와 에러 메세지로 매핑
            for (String key : validateMap.keySet()) {
                model.addAttribute(key, validateMap.get(key));
            }
            return "member/register";
        }

        userService.registerUser(registerDto);

        return "member/register-success";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth (
            @RequestParam String id, Model model
    ) {
        boolean result = userService.emailAuth(id);
        model.addAttribute("result", result);
        return "member/email-auth";
    }

    @GetMapping("/member/login")
    public String login() {
        return "member/login";
    }

    @PostMapping("/member/login")
    public String login(
            @RequestParam (value = "error", required = false) String error,
            @RequestParam (value = "exception", required = false) String exception,
            Model model
    ) {
        /* error, exception > model에 담아 view로 넘겨준다 */
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "member/login";
    }
}
