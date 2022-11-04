package ru.practicum.compilations.publicaccess;

import ru.practicum.compilations.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    List<CompilationDto> getAllCompilations(boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compilationId);

}
