package ru.practicum.events.admin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.specifications.model.AdminEventRequest;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@AllArgsConstructor
public class AdminEventController {

    private final AdminEventService adminService;

    @GetMapping
    public List<EventFullDto> getAllEvents(AdminEventRequest request) {
        return adminService.getAllEvents(request);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long eventId,
                                    @RequestBody NewEventDto eventDto) {
        return adminService.updateEvent(eventId, eventDto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        return adminService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        return adminService.rejectEvent(eventId);
    }
}
