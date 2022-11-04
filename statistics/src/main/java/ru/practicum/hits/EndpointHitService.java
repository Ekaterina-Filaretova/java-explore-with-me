package ru.practicum.hits;

import ru.practicum.hits.dto.EndpointHitDto;
import ru.practicum.hits.dto.ViewStatsDto;
import ru.practicum.hits.specifications.model.ViewStatsRequest;

import java.util.List;

public interface EndpointHitService {

    void addHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats(ViewStatsRequest request);
}
