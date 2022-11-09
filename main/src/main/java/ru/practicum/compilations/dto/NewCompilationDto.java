package ru.practicum.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    private List<Long> events;
    private boolean pinned;
    @NotEmpty(message = "Передано пустое название подборки")
    @NotEmpty(message = "Передано пустое название подборки")
    private String title;
}
