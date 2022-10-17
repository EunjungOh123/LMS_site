package com.example.coursesite_final.course.service;

import com.example.coursesite_final.course.dto.CourseDto;
import com.example.coursesite_final.course.dto.InputCourseDto;
import com.example.coursesite_final.course.model.CourseParam;
import com.example.coursesite_final.course.model.ServiceResult;
import com.example.coursesite_final.course.model.TakeCourseInput;

import java.util.List;

public interface CourseService {

    /**
     * 강좌 등록
     */
    void add (InputCourseDto courseDto);

    /**
     * 강좌 정보수정
     */
    boolean set (InputCourseDto courseDto);

    /**
     * 강좌 목록
     */
    List<CourseDto> list (CourseParam param);

    /**
     * 강좌 상세정보
     */
    CourseDto getById(long id);

    /**
     * 강좌 내용 삭제 (선택 삭제)
     */
    void delete (String idList);

    /**
     * 프론트 강좌 목록
     */
    List<CourseDto> frontList(CourseParam parameter);

    /**
     * 프론트 강좌 상세 정보
     */
    CourseDto frontDetail(long id);

    /**
     * 수강신청
     */
    ServiceResult req(TakeCourseInput courseInput);

    /**
     * 전체 강좌 목록
     */
    List<CourseDto> listAll();
}
