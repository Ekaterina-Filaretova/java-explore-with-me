package ru.practicum.events.specifications;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.practicum.events.Event;
import ru.practicum.events.EventState;
import ru.practicum.events.specifications.model.AdminEventRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class AdminEventSpecifications implements Specification<Event> {

    private AdminEventRequest request;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (request.getUsers() != null && request.getUsers().length > 0) {
            predicates.add(root.get("initiator").get("id").in(List.of(request.getUsers())));
        }

        if (request.getStates() != null && request.getStates().length > 0) {
            predicates.add(root.get("state").in(List.of(convertToState(request.getStates()))));
        }

        if (request.getCategories() != null && request.getCategories().length > 0) {
            predicates.add(root.get("category").get("id").in(List.of(request.getCategories())));
        }

        if (request.getRangeStart() != null && request.getRangeEnd() != null) {
            LocalDateTime start = parseString(request.getRangeStart());
            LocalDateTime end = parseString(request.getRangeEnd());
            predicates.add(builder.between(root.get("eventDate"), start, end));
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private LocalDateTime parseString(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private List<EventState> convertToState(String[] strings) {
        List<EventState> states = new ArrayList<>();
        for (String state : strings) {
            if (EventState.valueOf(state).name().equals(state)) {
                states.add(EventState.valueOf(state));
            }
        }
        return states;
    }
}
