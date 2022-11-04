package ru.practicum.compilations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.events.EventMapper;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;

    public CompilationDto convertToDto(Compilation compilation) {
        return new CompilationDto(eventMapper.convertToShortDto(compilation.getEvents()),
                compilation.getId(),
                compilation.isPinned(),
                compilation.getTitle());
    }

    public Compilation convertToEntity(NewCompilationDto compilationDto) {
        return new Compilation(null,
                new ArrayList<>(),
                compilationDto.isPinned(),
                compilationDto.getTitle());
    }

    public List<CompilationDto> convertToDto(List<Compilation> compilations) {
        List<CompilationDto> compilationDtos = new ArrayList<>();
        for (Compilation compilation : compilations) {
            compilationDtos.add(convertToDto(compilation));
        }
        return compilationDtos;
    }
}
