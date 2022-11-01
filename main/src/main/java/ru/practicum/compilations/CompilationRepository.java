package ru.practicum.compilations;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Modifying
    @Query(value = "delete from compilation_event c where c.compilation_id = ?1 and c.event_id = ?2",
            nativeQuery = true)
    void deleteCompilationEventById(Long compilationId, Long eventId);

    @Modifying
    @Query(value = "insert into compilation_event (compilation_id, event_id) values (?1, ?2)",
            nativeQuery = true)
    void addCompilationEventById(Long compilationId, Long eventId);

    List<Compilation> findAllByPinned(boolean pinned, Pageable pageable);

}
