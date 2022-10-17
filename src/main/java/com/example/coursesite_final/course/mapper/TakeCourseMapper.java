package com.example.coursesite_final.course.mapper;

import com.example.coursesite_final.course.dto.TakeCourseDto;
import com.example.coursesite_final.course.model.TakeCourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TakeCourseMapper {
    long selectListCount(TakeCourseParam parameter);
    List<TakeCourseDto> selectList(TakeCourseParam parameter);

    List<TakeCourseDto> selectListMyCourse(TakeCourseParam parameter);
}
