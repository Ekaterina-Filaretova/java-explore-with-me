package ru.practicum.requests;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.Event;
import ru.practicum.events.EventState;
import ru.practicum.events.privateaccess.PrivateEventService;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.users.AdminUserService;
import ru.practicum.users.User;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = @Lazy)
public class PrivateParticipationRequestServiceImpl implements PrivateParticipationRequestService {

    private final ParticipationRequestMapper requestMapper;
    private final PrivateEventService eventService;
    private final AdminUserService userService;
    private final ParticipationRequestRepository requestRepository;

    @Override
    public List<ParticipationRequestDto> getAllUserRequests(Long userId) {
        userService.getUserById(userId);
        List<ParticipationRequest> requests = requestRepository.findAllByRequesterId(userId);
        log.info("Найдены заявки {}", requests);
        return requestMapper.convertToDto(requests);
    }

    @Override
    @Transactional
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);
        checkRequest(event, userId);
        ParticipationRequest request = new ParticipationRequest();
        request.setEvent(event);
        request.setRequester(user);
        request.setCreated(LocalDateTime.now());
        if (event.getRequestModeration()) {
            request.setStatus(RequestStatus.PENDING);
        } else {
            request.setStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        requestRepository.save(request);
        log.info("Добавлена новая заявка {}", request);
        return requestMapper.convertToDto(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        ParticipationRequest request = getRequestById(requestId);
        if (!request.getRequester().getId().equals(userId)) {
            throw new ValidationException("Нельзя отменить чужую заявку");
        }
        switch (request.getStatus()) {
            case CANCELED:
                throw new ValidationException("Заявка уже отменена");
            case REJECTED:
                throw new ValidationException("Заявка уже отклонена");
            case CONFIRMED:
                Event event = eventService.getEventById(request.getEvent().getId());
                event.setConfirmedRequests(event.getConfirmedRequests() - 1);
        }
        request.setStatus(RequestStatus.CANCELED);
        log.info("Отменена заявка {}", request);
        return requestMapper.convertToDto(request);
    }

    @Override
    public ParticipationRequest getRequestById(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Отсутствует заявка с id " + requestId));
    }

    @Override
    public List<ParticipationRequest> getAllByEvent(Long eventId) {
        return requestRepository.findAllByEventId(eventId);
    }

    @Override
    @Transactional
    public void rejectAllRequests(Long eventId) {
        List<ParticipationRequest> requests = requestRepository.findAllByEventId(eventId);
        for (ParticipationRequest request : requests) {
            request.setStatus(RequestStatus.CANCELED);
        }
    }

    private void checkRequest(Event event, Long userId) {
        if (event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Нельзя добавить заявку на свое событие");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ValidationException("Событие ещё не опубликовано");
        }
        if (event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new ValidationException("Достигнут лимит заявок");
        }
    }
}
