package ru.practicum.requests;

import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateParticipationRequestService {

    List<ParticipationRequestDto> getAllUserRequests(Long userId);

    ParticipationRequestDto addRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    ParticipationRequest getRequestById(Long requestId);

    List<ParticipationRequest> getAllByEvent(Long eventId);

    void rejectAllRequests(Long eventId);
}
