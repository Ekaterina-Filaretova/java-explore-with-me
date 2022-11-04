package ru.practicum.events.privateaccess;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.requests.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/users/{userId}/events")
@AllArgsConstructor
public class PrivateEventController {

    private final PrivateEventService privateService;

    @GetMapping
    public List<EventShortDto> getAllEvents(@PathVariable Long userId,
                                            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0")
                                            Integer from,
                                            @Positive @RequestParam(value = "size", defaultValue = "10")
                                            Integer size) {
        return privateService.getAllEvents(userId, from, size);
    }

    @PatchMapping
    public EventFullDto updateEvent(@PathVariable Long userId,
                                    @Valid @RequestBody NewEventDto eventDto) {
        return privateService.updateEvent(userId, eventDto);
    }

    @PostMapping
    public EventFullDto addEvent(@PathVariable Long userId,
                                 @Valid @RequestBody NewEventDto eventDto) {
        return privateService.addEvent(userId, eventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByUser(@PathVariable Long userId,
                                       @PathVariable Long eventId) {
        return privateService.getEventByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId) {
        return privateService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable Long userId,
                                                          @PathVariable Long eventId) {
        return privateService.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable Long userId,
                                                  @PathVariable Long eventId,
                                                  @PathVariable Long reqId) {
        return privateService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable Long userId,
                                                 @PathVariable Long eventId,
                                                 @PathVariable Long reqId) {
        return privateService.rejectRequest(userId, eventId, reqId);
    }
}