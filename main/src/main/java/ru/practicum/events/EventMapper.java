package ru.practicum.events;

import org.springframework.stereotype.Component;
import ru.practicum.categories.Category;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.users.dto.UserShortDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {

    public EventShortDto convertToShortDto(Event event) {
        return new EventShortDto(
                event.getAnnotation(),
                new CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                parseDate(event.getEventDate()),
                event.getId(),
                new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.getPaid(),
                event.getTitle(),
                event.getViews());
    }

    public List<EventShortDto> convertToShortDto(List<Event> events) {
        List<EventShortDto> eventDtos = new ArrayList<>();
        for (Event event : events) {
            eventDtos.add(convertToShortDto(event));
        }
        return eventDtos;
    }

    public EventFullDto convertToDto(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                new CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                parseDate(event.getCreatedOn()),
                event.getDescription(),
                parseDate(event.getEventDate()),
                event.getId(),
                new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                parseDate(event.getPublishedOn()),
                event.getRequestModeration(),
                event.getState().toString(),
                event.getTitle(),
                event.getViews());
    }

    public List<EventFullDto> convertToDto(List<Event> events) {
        List<EventFullDto> eventDtos = new ArrayList<>();
        for (Event event : events) {
            eventDtos.add(convertToDto(event));
        }
        return eventDtos;
    }

    public Event convertToEntity(NewEventDto eventDto) {
        return new Event(eventDto.getEventId(),
                eventDto.getAnnotation(),
                new Category(eventDto.getCategory(), null),
                0,
                LocalDateTime.now(),
                eventDto.getDescription(),
                parseString(eventDto.getEventDate()),
                null,
                eventDto.getLocation(),
                eventDto.getPaid(),
                eventDto.getParticipantLimit(),
                null,
                eventDto.getRequestModeration(),
                EventState.PENDING,
                eventDto.getTitle(),
                0);
    }

    private String parseDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private LocalDateTime parseString(String date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
