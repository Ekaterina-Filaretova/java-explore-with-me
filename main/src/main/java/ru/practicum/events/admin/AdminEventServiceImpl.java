package ru.practicum.events.admin;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.CategoryMapper;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.publicaccess.PublicCategoryService;
import ru.practicum.events.Event;
import ru.practicum.events.EventMapper;
import ru.practicum.events.EventRepository;
import ru.practicum.events.EventState;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.specifications.AdminEventSpecifications;
import ru.practicum.events.specifications.model.AdminEventRequest;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final PublicCategoryService categoryService;
    private final EventRepository eventRepository;

    @Override
    public List<EventFullDto> getAllEvents(@NonNull AdminEventRequest request) {
        List<Event> events = eventRepository.findAll(new AdminEventSpecifications(request));
        log.info("Найдены события {}", events);
        return eventMapper.convertToDto(events);
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long eventId, NewEventDto eventDto) {
        Event event = getById(eventId);
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategory() != null) {
            CategoryDto categoryDto = categoryService.getCategoryById(eventDto.getCategory());
            event.setCategory(categoryMapper.convertToEntity(categoryDto));
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getEventDate() != null) {
            event.setEventDate(parseString(eventDto.getEventDate()));
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getParticipantLimit() != 0) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        if (eventDto.getLocation() != null) {
            event.setLocation(eventDto.getLocation());
        }
        if (eventDto.getRequestModeration() != null) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }
        log.info("Событие обновлено {}", event);
        return eventMapper.convertToDto(event);
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(Long eventId) {
        Event event = getById(eventId);
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ValidationException("Дата начала события должна быть не ранее чем за час от даты публикации");
        }
        if (!event.getState().equals(EventState.PENDING)) {
            throw new ValidationException("Событие должно быть в состоянии ожидания публикации");
        }
        event.setPublishedOn(LocalDateTime.now());
        event.setState(EventState.PUBLISHED);
        log.info("Событие опубликовано {}", event);
        return eventMapper.convertToDto(event);
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(Long eventId) {
        Event event = getById(eventId);
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("Событие не должно быть со статусом опубликовано");
        }
        event.setState(EventState.CANCELED);
        log.info("Событие отклонено {}", event);
        return eventMapper.convertToDto(event);
    }

    private Event getById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Отсутствует событие с id " + eventId));
    }

    private LocalDateTime parseString(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
