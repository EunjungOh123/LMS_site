package com.example.coursesite_final.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminMainController {

    @GetMapping("/admin/main") // 관리자 메인 페이지
    public String main() {
        return "admin/main";
    }
}
