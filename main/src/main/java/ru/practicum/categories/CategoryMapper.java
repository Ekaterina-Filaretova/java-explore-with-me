package ru.practicum.categories;

import org.springframework.stereotype.Component;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    public CategoryDto convertToDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public List<CategoryDto> convertToDto(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(convertToDto(category));
        }
        return categoryDtos;
    }

    public Category convertToEntity(NewCategoryDto categoryDto) {
        return new Category(null, categoryDto.getName());
    }

    public Category convertToEntity(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
