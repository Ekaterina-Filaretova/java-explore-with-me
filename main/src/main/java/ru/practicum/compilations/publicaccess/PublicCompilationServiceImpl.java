package ru.practicum.compilations.publicaccess;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.Compilation;
import ru.practicum.compilations.CompilationMapper;
import ru.practicum.compilations.CompilationRepository;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.exceptions.ObjectNotFoundException;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationMapper compilationMapper;
    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> getAllCompilations(boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.unsorted());
        List<Compilation> compilations = compilationRepository.findAllByPinned(pinned, pageable);
        log.info("Найдены подборки {}", compilations);
        return compilationMapper.convertToDto(compilations);
    }

    @Override
    public CompilationDto getCompilationById(Long compilationId) {
        Compilation compilation = compilationRepository.findById(compilationId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Отсутствует подборка с id " + compilationId));
        log.info("Найдена подборка {}", compilation);
        return compilationMapper.convertToDto(compilation);
    }
}
