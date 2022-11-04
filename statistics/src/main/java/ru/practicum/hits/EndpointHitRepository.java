package ru.practicum.hits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long>, JpaSpecificationExecutor<EndpointHit> {

    int countByUri(String uri);

}
