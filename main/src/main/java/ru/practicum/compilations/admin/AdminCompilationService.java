package ru.practicum.compilations.admin;

import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

public interface AdminCompilationService {

    CompilationDto addCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(Long compilationId);

    void deleteCompilationEvent(Long compilationId, Long eventId);

    void addCompilationEvent(Long compilationId, Long eventId);

    void unpinCompilation(Long compilationId);

    void pinCompilation(Long compilationId);
}
