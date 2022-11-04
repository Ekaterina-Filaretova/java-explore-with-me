package ru.practicum.categories.admin;

import ru.practicum.categories.Category;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;

public interface AdminCategoryService {

    CategoryDto updateCategory(CategoryDto categoryDto);

    CategoryDto addCategory(NewCategoryDto categoryDto);

    void deleteCategory(Long categoryId);

    Category getCategoryById(Long categoryId);
}
