package ru.practicum.events.privateaccess;

import ru.practicum.events.Event;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> getAllEvents(Long userId, Integer from, Integer size);

    EventFullDto updateEvent(Long userId, NewEventDto eventDto);

    EventFullDto addEvent(Long userId, NewEventDto eventDto);

    EventFullDto getEventByUser(Long userId, Long eventId);

    EventFullDto cancelEvent(Long userId, Long eventId);

    List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId);

    Event getEventById(Long eventId);

    List<Event> getEventsById(List<Long> ids);
}
