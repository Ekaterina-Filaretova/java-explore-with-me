package ru.practicum.events.specifications;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.events.Event;
import ru.practicum.events.EventState;
import ru.practicum.events.specifications.model.PublicEventRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PublicEventSpecifications implements Specification<Event> {

    private final PublicEventRequest request;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (request.getText() == null || request.getText().length() < 2) {
            predicates.add(builder.or(
                    builder.like(builder.lower(root.get("annotation")), "%" + request.getText().toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("description")), "%" + request.getText().toLowerCase() + "%")
            ));
        }
        if (request.getCategories() != null && request.getCategories().length > 0) {
            predicates.add(root.get("category").get("id").in(List.of(request.getCategories())));
        }
        if (request.getPaid() != null) {
            predicates.add(builder.equal(root.get("paid"), request.getPaid()));
        }
        if (request.getRangeStart() == null || request.getRangeEnd() == null) {
            predicates.add(builder.greaterThan(root.get("eventDate"), LocalDateTime.now()));
        } else {
            LocalDateTime start = parseString(request.getRangeStart());
            LocalDateTime end = parseString(request.getRangeEnd());
            predicates.add(builder.between(root.get("eventDate"), start, end));
        }
        if (request.isOnlyAvailable()) {
            predicates.add(builder.greaterThan(root.get("participantLimit"), root.get("confirmedRequests")));
        } else {
            predicates.add(builder.lessThan(root.get("confirmedRequests"), root.get("participantLimit")));
        }
        predicates.add(builder.equal(root.get("state"), EventState.PUBLISHED));
        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private LocalDateTime parseString(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
