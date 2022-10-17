package com.example.coursesite_final.admin.dto;

import com.example.coursesite_final.admin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class
CategoryDto {

    Long id;
    String categoryName;
    int sortValue;
    boolean usingYn;

    // 추가
    int courseCount;

    public static List<CategoryDto> fromEntity (List<Category> categories) {
        if (categories != null) {
            List<CategoryDto> categoryList = new ArrayList<>();
            for(Category x : categories) {
                categoryList.add(fromEntity(x));
            }
            return categoryList;
        }
        return null;
    }

    public static CategoryDto fromEntity (Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .sortValue(category.getSortValue())
                .usingYn(category.isUsingYn())
                .build();
    }
}
