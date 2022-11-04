package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.location.Location;
import ru.practicum.users.dto.UserShortDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {

    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private String state;
    private String title;
    private int views;

}
