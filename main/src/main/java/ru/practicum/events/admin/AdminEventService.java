package ru.practicum.events.admin;

import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.specifications.model.AdminEventRequest;

import java.util.List;

public interface AdminEventService {

    List<EventFullDto> getAllEvents(AdminEventRequest request);

    EventFullDto updateEvent(Long eventId, NewEventDto eventDto);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

}
