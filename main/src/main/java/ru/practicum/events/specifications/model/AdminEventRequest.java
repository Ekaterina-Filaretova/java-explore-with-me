package ru.practicum.events.specifications.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminEventRequest {

    private Long[] users;
    private String[] states;
    private Long[] categories;
    private String rangeStart;
    private String rangeEnd;
    private Integer from = 0;
    private Integer size = 10;
}
