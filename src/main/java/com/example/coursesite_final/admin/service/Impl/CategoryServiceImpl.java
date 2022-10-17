package com.example.coursesite_final.admin.service.Impl;

import com.example.coursesite_final.admin.dto.CategoryDto;
import com.example.coursesite_final.admin.dto.InputCategoryDto;
import com.example.coursesite_final.admin.entity.Category;
import com.example.coursesite_final.admin.mapper.CategoryMapper;
import com.example.coursesite_final.admin.repository.CategoryRepository;
import com.example.coursesite_final.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /* Spring Data JPA에서는 쿼리 메소드에 정렬 기능을 제공하는 클래스 있음 */

    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC, "sortValue"); // 내림차순 정렬
    }

    @Override
    public List<CategoryDto> list() {
        List<Category> categories = categoryRepository.findAll(getSortBySortValueDesc());
        return CategoryDto.fromEntity(categories);
    }

    @Override
    public void add(InputCategoryDto categoryDto) {
        Category category = categoryDto.toEntity();
        categoryRepository.save(category);
    }

    @Override
    public void update(InputCategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getId());

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategoryName(categoryDto.getCategoryName());
            category.setSortValue(categoryDto.getSortValue());
            category.setUsingYn(categoryDto.isUsingYn());
            categoryRepository.save(category);
        }
    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }
    @Override
    public boolean validateCategoryName (String categoryName) {
        if(categoryRepository.existsByCategoryName(categoryName)) {
            return false;
        }
        return true;
    }

    @Override
    public List<CategoryDto> frontList(CategoryDto categoryDto) {
        return categoryMapper.select(categoryDto);
    }
}
