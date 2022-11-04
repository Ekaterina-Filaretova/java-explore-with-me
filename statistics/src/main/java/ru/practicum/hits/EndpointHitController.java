package ru.practicum.hits;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.hits.dto.EndpointHitDto;
import ru.practicum.hits.dto.ViewStatsDto;
import ru.practicum.hits.specifications.model.ViewStatsRequest;

import java.util.List;

@RestController
@AllArgsConstructor
public class EndpointHitController {

    private final EndpointHitService hitService;

    @PostMapping("/hit")
    public void addHit(@RequestBody EndpointHitDto endpointHitDto) {
        hitService.addHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(ViewStatsRequest request) {
        return hitService.getStats(request);
    }
}
