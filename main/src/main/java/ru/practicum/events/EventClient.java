package ru.practicum.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.events.dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EventClient {

    private final RestTemplate template;

    public EventClient(@Value("${explore-with-me-statistics.url}") String url,
                       RestTemplateBuilder template) {
        this.template = template.uriTemplateHandler(new DefaultUriBuilderFactory(url)).build();
    }

    public void addHit(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<EndpointHitDto> entity = new HttpEntity<>(makeBody(request), headers);
        template.postForEntity("/hit", entity, EndpointHitDto.class);
    }

    private EndpointHitDto makeBody(HttpServletRequest request) {
        return new EndpointHitDto("ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
