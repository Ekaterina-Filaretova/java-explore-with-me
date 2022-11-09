package ru.practicum.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    @NotEmpty(message = "Передано пустое название категории")
    @NotEmpty(message = "Передано пустое название категории")
    private String name;
}
