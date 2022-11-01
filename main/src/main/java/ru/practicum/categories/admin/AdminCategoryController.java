package ru.practicum.categories.admin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;

@RestController
@RequestMapping(path = "/admin/categories")
@AllArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminService;

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        return adminService.updateCategory(categoryDto);
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody NewCategoryDto newCategoryDto) {
        return adminService.addCategory(newCategoryDto);
    }

    @DeleteMapping(path = "/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        adminService.deleteCategory(categoryId);
    }
}
