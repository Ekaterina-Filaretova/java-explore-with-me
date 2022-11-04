package ru.practicum.hits.specifications;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.practicum.hits.EndpointHit;
import ru.practicum.hits.specifications.model.ViewStatsRequest;

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
public class ViewStatsSpecifications implements Specification<EndpointHit> {

    private ViewStatsRequest request;

    @Override
    public Predicate toPredicate(Root<EndpointHit> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (request.getStart() != null && request.getEnd() != null) {
            LocalDateTime start = parseString(request.getStart());
            LocalDateTime end = parseString(request.getEnd());
            predicates.add(builder.between(root.get("timestamp"), start, end));
        }
        if (request.getUris() != null && request.getUris().length > 0) {
            predicates.add(root.get("uri").in(List.of(request.getUris())));
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private LocalDateTime parseString(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
