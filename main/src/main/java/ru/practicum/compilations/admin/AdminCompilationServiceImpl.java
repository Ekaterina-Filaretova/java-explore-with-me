package ru.practicum.compilations.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilations.Compilation;
import ru.practicum.compilations.CompilationMapper;
import ru.practicum.compilations.CompilationRepository;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.events.privateaccess.PrivateEventService;
import ru.practicum.exceptions.ObjectNotFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationMapper compilationMapper;
    private final PrivateEventService eventService;
    private final CompilationRepository compilationRepository;

    @Override
    public CompilationDto addCompilation(NewCompilationDto compilationDto) {
        Compilation compilation = compilationMapper.convertToEntity(compilationDto);
        compilation.setEvents(eventService.getEventsById(compilationDto.getEvents()));
        compilationRepository.save(compilation);
        log.info("Новая подборка добавлена {}", compilation);
        return compilationMapper.convertToDto(compilation);
    }

    @Override
    public void deleteCompilation(Long compilationId) {
        Compilation compilation = getById(compilationId);
        compilationRepository.deleteById(compilationId);
        log.info("Подборка удалена {}", compilation);
    }

    @Override
    @Transactional
    public void deleteCompilationEvent(Long compilationId, Long eventId) {
        getById(compilationId);
        compilationRepository.deleteCompilationEventById(compilationId, eventId);
        log.info("Событие с id {} удалено из подборки с id {}", eventId, compilationId);
    }

    @Override
    @Transactional
    public void addCompilationEvent(Long compilationId, Long eventId) {
        getById(compilationId);
        compilationRepository.addCompilationEventById(compilationId, eventId);
        log.info("Событие с id {} добавлено в подборку с id {}", eventId, compilationId);
    }

    @Override
    @Transactional
    public void unpinCompilation(Long compilationId) {
        Compilation compilation = getById(compilationId);
        compilation.setPinned(false);
        log.info("Откреплена подборка {}", compilation);
    }

    @Override
    @Transactional
    public void pinCompilation(Long compilationId) {
        Compilation compilation = getById(compilationId);
        compilation.setPinned(true);
        log.info("Закреплена подборка {}", compilation);
    }

    private Compilation getById(Long compilationId) {
        return compilationRepository.findById(compilationId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Отсутствует подборка с id " + compilationId));
    }
}
