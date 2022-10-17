package com.example.coursesite_final.admin.controller;

import com.example.coursesite_final.admin.dto.CategoryDto;
import com.example.coursesite_final.admin.dto.InputCategoryDto;
import com.example.coursesite_final.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/category/list")
    public String categoryList(Model model) {

        List<CategoryDto> list = categoryService.list();
        model.addAttribute("list", list);

        return "admin/category/list";
    }

    @PostMapping("/admin/category/add")
    public String add(InputCategoryDto categoryDto) {

        boolean result = categoryService.validateCategoryName(categoryDto.getCategoryName());

        if(result) {
            categoryService.add(categoryDto);
        }

        return "redirect:/admin/category/list";
    }

    @PostMapping("/admin/category/update")
    public String update(InputCategoryDto categoryDto) {

        categoryService.update(categoryDto);

        return "redirect:/admin/category/list";
    }

    @PostMapping("/admin/category/delete")
    public String delete(InputCategoryDto categoryDto) {

        categoryService.delete(categoryDto.getId());

        return "redirect:/admin/category/list";
    }
}
