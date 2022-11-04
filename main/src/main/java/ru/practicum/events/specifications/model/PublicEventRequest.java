package ru.practicum.events.specifications.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublicEventRequest {

    private String text;
    private Long[] categories;
    private Boolean paid;
    private String rangeStart;
    private String rangeEnd;
    private boolean onlyAvailable = false;
    private String sort;
    private Integer from = 0;
    private Integer size = 10;
}
