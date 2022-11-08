package ru.practicum.events;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    List<Event> findAllByIdIn(List<Long> ids);

    List<Event> findAllByInitiatorIdAndStateIs(Long userId, EventState state, Pageable pageable);

}
