package com.example.coursesite_final.course.model;

import com.example.coursesite_final.admin.model.CommonParam;
import lombok.Data;

@Data
public class CourseParam extends CommonParam {
    long id; // course_id
    long categoryId;
}
