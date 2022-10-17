package com.example.coursesite_final.admin.controller;

import com.example.coursesite_final.admin.dto.ChangeUserDetailDto;
import com.example.coursesite_final.admin.dto.UserDto;
import com.example.coursesite_final.admin.model.UserParam;
import com.example.coursesite_final.course.controller.PageController;
import com.example.coursesite_final.history.dto.HistoryDto;
import com.example.coursesite_final.history.service.HistoryService;
import com.example.coursesite_final.member.service.UserService;
import com.example.coursesite_final.member.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminUserController extends PageController {

    private final UserService userService;
    private final UserSettingsService userSettingsService;
    private final HistoryService historyService;

    @GetMapping("/admin/member/list")
    public String list (Model model, UserParam param) {

        param.init();

        List<UserDto> users = userService.list(param);

        long totalCount = 0;
        if(users != null && users.size()>0) {
            totalCount = users.get(0).getTotalCount();
        }

        String queryString = param.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, param.getPageSize(), param.getPageIndex(), queryString);

        model.addAttribute("list", users);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/member/list";
    }
    @GetMapping("/admin/member/detail")
    public String detail(Model model, UserParam param) {
        param.init();
        UserDto user = userSettingsService.detail(param.getUserId());
        List<HistoryDto> log = historyService.detail(param.getUserId());

        long totalCount = 0;
        if(log != null && log.size()>0) {
            totalCount = log.get(0).getTotalCount();
        }

        model.addAttribute("user", user);
        model.addAttribute("log", log);
        model.addAttribute("totalCount",totalCount);

        return "admin/member/detail";
    }
    @PostMapping("/admin/member/status")
    public String status(ChangeUserDetailDto detailInfoDto) {


        userSettingsService.updateStatus(detailInfoDto.getUserId(), detailInfoDto.getUserStatus());

        return "redirect:/admin/member/detail?userId=" + detailInfoDto.getUserId();
    }

    @PostMapping("/admin/member/password")
    public String password(ChangeUserDetailDto detailInfoDto) {


        userSettingsService.updatePassword(detailInfoDto.getUserId(), detailInfoDto.getPassword());

        return "redirect:/admin/member/detail?userId=" + detailInfoDto.getUserId();
    }
}
