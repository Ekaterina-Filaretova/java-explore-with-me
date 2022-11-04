package ru.practicum.compilations.publicaccess;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@AllArgsConstructor
@Validated
public class PublicCompilationController {

    private final PublicCompilationService publicService;

    @GetMapping
    public List<CompilationDto> getAllCompilations(@RequestParam(value = "pinned", required = false)
                                                   boolean pinned,
                                                   @PositiveOrZero @RequestParam(value = "from", defaultValue = "0")
                                                   Integer from,
                                                   @Positive @RequestParam(value = "size", defaultValue = "10")
                                                   Integer size) {
        return publicService.getAllCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        return publicService.getCompilationById(compId);
    }
}