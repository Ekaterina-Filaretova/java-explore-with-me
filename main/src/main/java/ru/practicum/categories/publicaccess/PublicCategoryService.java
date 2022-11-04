package ru.practicum.categories.publicaccess;

import ru.practicum.categories.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {

    List<CategoryDto> getAllCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long categoryId);
}
