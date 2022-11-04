package ru.practicum.hits;

import org.springframework.stereotype.Component;
import ru.practicum.hits.dto.EndpointHitDto;
import ru.practicum.hits.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class EndpointHitMapper {

    public ViewStatsDto convertToDto(EndpointHit endpointHit) {
        return new ViewStatsDto(endpointHit.getApp(),
                endpointHit.getUri(),
                0);
    }

    public List<ViewStatsDto> convertToDto(List<EndpointHit> endpointHits) {
        List<ViewStatsDto> viewDtos = new ArrayList<>();
        for (EndpointHit hit : endpointHits) {
            viewDtos.add(convertToDto(hit));
        }
        return viewDtos;
    }

    public EndpointHit convertToEntity(EndpointHitDto endpointHitDto) {
        return new EndpointHit(null,
                endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                parseString(endpointHitDto.getTimestamp()));
    }

    private LocalDateTime parseString(String date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
