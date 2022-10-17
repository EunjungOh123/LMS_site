package com.example.coursesite_final.course.dto;

import lombok.Data;

@Data
public class InputCourseDto {
    long id;
    long categoryId;
    String subject;
    String keyword;
    String summary;
    String contents;
    long price;
    long salePrice;
    String saleEndDt;

    //삭제를 위한
    String idList;


    //ADD
    String filename;
    String urlFilename;
}
