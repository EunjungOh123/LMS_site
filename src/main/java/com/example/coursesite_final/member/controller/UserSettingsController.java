package com.example.coursesite_final.member.controller;

import com.example.coursesite_final.admin.dto.UserDto;
import com.example.coursesite_final.course.dto.TakeCourseDto;
import com.example.coursesite_final.course.model.ServiceResult;
import com.example.coursesite_final.course.service.TakeCourseService;
import com.example.coursesite_final.member.dto.FindPasswordDto;
import com.example.coursesite_final.member.dto.ResetPasswordDto;
import com.example.coursesite_final.member.dto.UserInputDto;
import com.example.coursesite_final.member.exception.ResetPasswordException;
import com.example.coursesite_final.member.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/* 비밀번호 재설정 */

@Controller
@RequiredArgsConstructor
public class UserSettingsController {
    private final UserSettingsService userSettingsService;
    private final TakeCourseService takeCourseService;

    @GetMapping("/member/reset-password")
    public String resetPassword(@RequestParam String id, Model model) {
        boolean result = userSettingsService.checkResetPasswordKey(id);
        model.addAttribute("result",result);
        return "member/reset-password";
    }

    @PostMapping("/member/reset-password")
    public String resetPasswordSubmit(
            Model model, @Valid ResetPasswordDto resetPasswordDto,
            Errors errors, FindPasswordDto findPasswordDto
    ) {
        boolean result = false;

        if (errors.hasErrors()) {
            Map<String, String> validateMap = userSettingsService.validateHandling(errors);
            for (String key : validateMap.keySet()) {
                model.addAttribute(key, validateMap.get(key));
            }
            return "member/reset-password";
        }
        try {
            result = userSettingsService.resetPassword(findPasswordDto.getId(), findPasswordDto.getPassword());
        } catch (ResetPasswordException e) {
            System.out.println(e.getMessage());
        }
        model.addAttribute("result", result);
        return "member/reset-password-result";
    }
    @GetMapping("/member/info")
    public String memberInfo(Model model, Principal principal) {

        String userId = principal.getName();
        UserDto detail = userSettingsService.detail(userId);

        model.addAttribute("detail", detail);

        return "member/info";
    }
    @PostMapping("/member/info")
    public String memberInfoSubmit(Model model
            , UserInputDto parameter
            , Principal principal) {

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = userSettingsService.updateUser(parameter);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "error/common";
        }
        return "redirect:/member/info";
    }

    @GetMapping("/member/password")
    public String memberPassword(Model model, Principal principal) {

        String userId = principal.getName();
        UserDto detail = userSettingsService.detail(userId);

        model.addAttribute("detail", detail);

        return "member/password";
    }

    @PostMapping("/member/password")
    public String memberPasswordSubmit(Model model
            , UserInputDto parameter
            , Principal principal) {

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = userSettingsService.updateUserPassword(parameter);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "error/common";
        }

        return "redirect:/member/info";
    }
    @GetMapping("/member/takecourse")
    public String memberTakeCourse(Model model, Principal principal) {

        String userId = principal.getName();
        List<TakeCourseDto> list = takeCourseService.myCourse(userId);

        model.addAttribute("list", list);

        return "member/takecourse";
    }
    @GetMapping("/member/withdraw")
    public String memberWithdraw(Model model) {
        return "member/withdraw";
    }

    @PostMapping("/member/withdraw")
    public String memberWithdrawSubmit(Model model
            , UserInputDto parameter
            , Principal principal) {

        String userId = principal.getName();

        ServiceResult result = userSettingsService.withdraw(userId, parameter.getPassword());
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "error/common";
        }

        return "redirect:/member/logout";
    }
}