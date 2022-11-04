package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.events.location.Location;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {

    @Size(max = 2000, min = 20)
    private String annotation;
    private Long category;
    @Size(max = 7000, min = 20)
    private String description;
    private String eventDate;
    private Long eventId;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    @Size(max = 120, min = 3)
    private String title;
}
