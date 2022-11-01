package ru.practicum.events.publicaccess;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.EventClient;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.specifications.model.PublicEventRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@AllArgsConstructor
public class PublicEventController {

    private final PublicEventService publicService;
    private final EventClient eventClient;

    @GetMapping
    public List<EventShortDto> getAllEvents(PublicEventRequest request,
                                            HttpServletRequest servletRequest) {
        eventClient.addHit(servletRequest);
        return publicService.getAllEvents(request);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable(name = "id") Long eventId,
                                     HttpServletRequest servletRequest) {
        eventClient.addHit(servletRequest);
        return publicService.getEventById(eventId);
    }
}
