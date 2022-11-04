package ru.practicum.categories.publicaccess;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/categories")
public class PublicCategoryController {

    private final PublicCategoryService publicService;

    public PublicCategoryController(PublicCategoryService publicService) {
        this.publicService = publicService;
    }

    @GetMapping
    public List<CategoryDto> getAllCategories(@PositiveOrZero @RequestParam(value = "from", defaultValue = "0")
                                              Integer from,
                                              @Positive @RequestParam(value = "size", defaultValue = "10")
                                              Integer size) {
        return publicService.getAllCategories(from, size);
    }

    @GetMapping(path = "/{categoryId}")
    public CategoryDto getCategoryById(@PathVariable Long categoryId) {
        return publicService.getCategoryById(categoryId);
    }
}
