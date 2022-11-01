package ru.practicum.events.privateaccess;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.Category;
import ru.practicum.categories.admin.AdminCategoryService;
import ru.practicum.events.Event;
import ru.practicum.events.EventMapper;
import ru.practicum.events.EventRepository;
import ru.practicum.events.EventState;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.location.LocationService;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.requests.ParticipationRequest;
import ru.practicum.requests.ParticipationRequestMapper;
import ru.practicum.requests.PrivateParticipationRequestService;
import ru.practicum.requests.RequestStatus;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.users.AdminUserService;
import ru.practicum.users.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventMapper eventMapper;
    private final ParticipationRequestMapper requestMapper;
    private final AdminUserService userService;
    private final AdminCategoryService categoryService;
    private final PrivateParticipationRequestService requestService;
    private final LocationService locationService;
    private final EventRepository eventRepository;

    @Override
    public List<EventShortDto> getAllEvents(Long userId, Integer from, Integer size) {
        userService.getUserById(userId);
        Pageable pageable = PageRequest.of(from, size, Sort.unsorted());
        List<Event> events = eventRepository.findAllByInitiatorId(userId, pageable);
        log.info("Найдены события {}", events);
        return eventMapper.convertToShortDto(events);
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long userId, NewEventDto eventDto) {
        Event event = getEventById(eventDto.getEventId());
        checkUpdatedEvent(userId, event, eventDto.getEventDate());
        if (event.getState().equals(EventState.CANCELED)) {
            event.setState(EventState.PENDING);
        }
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategory() != null) {
            Category category = categoryService.getCategoryById(eventDto.getCategory());
            event.setCategory(category);
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
        log.info("Событие обновлено {}", event);
        return eventMapper.convertToDto(event);
    }

    @Override
    @Transactional
    public EventFullDto addEvent(Long userId, NewEventDto eventDto) {
        checkEventDate(eventDto.getEventDate());
        User initiator = userService.getUserById(userId);
        Category category = categoryService.getCategoryById(eventDto.getCategory());
        Event event = eventMapper.convertToEntity(eventDto);
        event.setInitiator(initiator);
        event.setCategory(category);
        locationService.saveLocation(event.getLocation());
        eventRepository.save(event);
        log.info("Добавлено новое событие {}", event);
        return eventMapper.convertToDto(event);
    }

    @Override
    public EventFullDto getEventByUser(Long userId, Long eventId) {
        Event event = getEventById(eventId);
        checkInitiator(userId, event);
        log.info("Найдено событие {}", event);
        return eventMapper.convertToDto(event);
    }

    @Override
    @Transactional
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        Event event = getEventById(eventId);
        checkInitiator(userId, event);
        switch (event.getState()) {
            case CANCELED:
                throw new ValidationException("Событие уже отменено");
            case PUBLISHED:
                throw new ValidationException("Событие уже опубликовано");
        }
        event.setState(EventState.CANCELED);
        log.info("Событие отменено {}", event);
        return eventMapper.convertToDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId) {
        userService.getUserById(userId);
        Event event = getEventById(eventId);
        checkInitiator(userId, event);
        List<ParticipationRequest> requests = requestService.getAllByEvent(eventId);
        log.info("Получен список заявок {}", requests);
        return requestMapper.convertToDto(requests);
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId) {
        userService.getUserById(userId);
        Event event = getEventById(eventId);
        checkInitiator(userId, event);
        ParticipationRequest request = requestService.getRequestById(requestId);
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            return requestMapper.convertToDto(request);
        }
        if (event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new ValidationException("Максимальный лимит заявок уже достигнут");
        }
        request.setStatus(RequestStatus.CONFIRMED);
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        if (event.getConfirmedRequests() == event.getParticipantLimit()) {
            requestService.rejectAllRequests(eventId);
        }
        log.info("Заявка на событие одобрена {}", request);
        return requestMapper.convertToDto(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId) {
        userService.getUserById(userId);
        Event event = getEventById(eventId);
        checkInitiator(userId, event);
        ParticipationRequest request = requestService.getRequestById(requestId);
        switch (request.getStatus()) {
            case CANCELED:
                throw new ValidationException("Заявка уже отменена");
            case REJECTED:
                throw new ValidationException("Заявка уже отклонена");
            case CONFIRMED:
                event.setConfirmedRequests(event.getConfirmedRequests() - 1);
        }
        request.setStatus(RequestStatus.REJECTED);
        log.info("Заявка на событие отклонена {}", request);
        return requestMapper.convertToDto(request);
    }

    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Отсутствует событие с id " + eventId));
    }

    @Override
    public List<Event> getEventsById(List<Long> ids) {
        return eventRepository.findAllByIdIn(ids);
    }

    private void checkUpdatedEvent(Long userId, Event event, String date) {
        checkInitiator(userId, event);
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("Событие уже опубликовано");
        }
        checkEventDate(date);
    }

    private void checkInitiator(Long userId, Event event) {
        if (!userId.equals(event.getInitiator().getId())) {
            throw new ValidationException("Только инициатор может выполнить действие");
        }
    }

    private void checkEventDate(String date) {
        if (parseString(date).isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("До события должно быть минимум 2 часа");
        }
    }

    private LocalDateTime parseString(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
