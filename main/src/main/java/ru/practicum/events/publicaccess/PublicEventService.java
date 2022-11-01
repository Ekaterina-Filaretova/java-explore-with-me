package ru.practicum.events.publicaccess;

import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.specifications.model.PublicEventRequest;

import java.util.List;

public interface PublicEventService {

    List<EventShortDto> getAllEvents(PublicEventRequest request);

    EventFullDto getEventById(Long eventId);
}
