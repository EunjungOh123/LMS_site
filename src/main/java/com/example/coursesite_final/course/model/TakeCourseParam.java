package com.example.coursesite_final.course.model;

import com.example.coursesite_final.admin.model.CommonParam;
import lombok.Data;

@Data
public class TakeCourseParam extends CommonParam {

    long id;
    String status;

    String userId;


    long searchCourseId;
}
