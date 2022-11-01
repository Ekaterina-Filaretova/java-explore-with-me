package ru.practicum.requests;

import org.springframework.stereotype.Component;
import ru.practicum.events.Event;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.users.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParticipationRequestMapper {

    public ParticipationRequestDto convertToDto(ParticipationRequest request) {
        return new ParticipationRequestDto(parseDate(request.getCreated()),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getStatus().toString());
    }

    public ParticipationRequest convertToEntity(ParticipationRequestDto requestDto) {
        return new ParticipationRequest(requestDto.getId(),
                new Event(),
                new User(),
                parseString(requestDto.getCreated()),
                RequestStatus.valueOf(requestDto.getStatus()));
    }

    public List<ParticipationRequestDto> convertToDto(List<ParticipationRequest> requests) {
        List<ParticipationRequestDto> requestDtos = new ArrayList<>();
        for (ParticipationRequest request : requests) {
            requestDtos.add(convertToDto(request));
        }
        return requestDtos;
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
