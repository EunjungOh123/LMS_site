package com.example.coursesite_final.course.mapper;

import com.example.coursesite_final.course.dto.CourseDto;
import com.example.coursesite_final.course.model.CourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    long selectListCount(CourseParam param);
    List<CourseDto> selectList(CourseParam param);
}
