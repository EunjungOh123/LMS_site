package com.example.coursesite_final.member.controller;


import com.example.coursesite_final.common.model.ResponseResult;
import com.example.coursesite_final.course.dto.TakeCourseDto;
import com.example.coursesite_final.course.model.ServiceResult;
import com.example.coursesite_final.course.model.TakeCourseInput;
import com.example.coursesite_final.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ApiUserController {

    private final TakeCourseService takeCourseService;

    @PostMapping("/api/member/course/cancel.api")
    public ResponseEntity<?> cancelCourse(@RequestBody TakeCourseInput parameter
            , Principal principal) {

        String userId = principal.getName();

        //내 강좌인지 확인
        TakeCourseDto detail = takeCourseService.detail(parameter.getTakeCourseId());
        if (detail == null) {
            ResponseResult responseResult = new ResponseResult(false, "수강 신청 정보가 존재하지 않습니다.");
            return ResponseEntity.ok().body(responseResult);
        }

        if (userId == null || !userId.equals(detail.getUserId())) {
            ResponseResult responseResult = new ResponseResult(false, "본인의 수강 신청 정보만 취소할 수 있습니다.");
            return ResponseEntity.ok().body(responseResult);
        }

        ServiceResult result = takeCourseService.cancel(parameter.getTakeCourseId());
        if (!result.isResult()) {
            ResponseResult responseResult = new ResponseResult(false, result.getMessage());
            return ResponseEntity.ok().body(responseResult);
        }

        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }
}
