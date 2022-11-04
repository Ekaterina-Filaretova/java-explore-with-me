package ru.practicum.categories.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.categories.Category;
import ru.practicum.categories.CategoryMapper;
import ru.practicum.categories.CategoryRepository;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.exceptions.ObjectNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = getCategoryById(categoryDto.getId());
        category.setName(categoryDto.getName());
        log.info("Название категории обновлено {}", category);
        return categoryMapper.convertToDto(category);
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto categoryDto) {
        Category category = categoryMapper.convertToEntity(categoryDto);
        categoryRepository.save(category);
        log.info("Новая категория сохранена {}", category);
        return categoryMapper.convertToDto(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        categoryRepository.delete(category);
        log.info("Категория удалена {}", category);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Отсутствует категория с id " + categoryId));
    }
}
