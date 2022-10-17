package com.example.coursesite_final.admin.service;


import com.example.coursesite_final.admin.dto.CategoryDto;
import com.example.coursesite_final.admin.dto.InputCategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> list();

    /**
     * 카테고리 신규 추가
     */
    void add(InputCategoryDto categoryDto);

    /**
     * 카테고리 수정
     */
    void update (InputCategoryDto categoryDto);

    /**
     *  카테고리 삭제
     */
    void delete (long id);

    boolean validateCategoryName (String categoryName);

    /**
     * 프론트 카테고리 정보
     */
    List<CategoryDto> frontList(CategoryDto categoryDto);

}
