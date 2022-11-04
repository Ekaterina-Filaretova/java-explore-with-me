package ru.practicum.categories.publicaccess;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.categories.Category;
import ru.practicum.categories.CategoryMapper;
import ru.practicum.categories.CategoryRepository;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.exceptions.ObjectNotFoundException;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.unsorted());
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        log.info("Найдены категории {}", categories);
        return categoryMapper.convertToDto(categories);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Отсутствует категория с id " + categoryId));
        log.info("Найдена категория {}", category);
        return categoryMapper.convertToDto(category);
    }
}
