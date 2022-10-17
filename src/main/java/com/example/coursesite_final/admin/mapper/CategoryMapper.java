package com.example.coursesite_final.admin.mapper;
import com.example.coursesite_final.admin.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<CategoryDto> select(CategoryDto parameter);
}
