package ru.practicum.events.publicaccess;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.specifications.PublicEventSpecifications;
import ru.practicum.events.specifications.model.PublicEventRequest;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.exceptions.ValidationException;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    @Override
    public List<EventShortDto> getAllEvents(@NonNull PublicEventRequest request) {
        Pageable pageable = PageRequest
                .of(request.getFrom(), request.getSize(), getSorting(request.getSort()));
        List<Event> events = eventRepository.findAll(new PublicEventSpecifications(request), pageable).getContent();
        log.info("Найдены события {}", events);
        return eventMapper.convertToShortDto(events);
    }

    @Override
    @Transactional
    public EventFullDto getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Отсутствует событие с id " + eventId));
        if (event.getState() != EventState.PUBLISHED) {
            throw new ValidationException("Событие ещё не опубликовано");
        }
        event.setViews(event.getViews() + 1);
        log.info("Получено событие {}", event);
        return eventMapper.convertToDto(event);
    }

    private Sort getSorting(String sort) {
        if (sort == null) {
            return Sort.unsorted();
        }
        switch (EventSort.valueOf(sort)) {
            case EVENT_DATE:
                return Sort.by(Sort.Direction.ASC, "eventDate");
            case VIEWS:
                return Sort.by(Sort.Direction.DESC, "views");
            default:
                throw new ValidationException("Неверный способ сортировки");
        }
    }
}
