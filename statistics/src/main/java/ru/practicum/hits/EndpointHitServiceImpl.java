package ru.practicum.hits;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.hits.dto.EndpointHitDto;
import ru.practicum.hits.dto.ViewStatsDto;
import ru.practicum.hits.specifications.ViewStatsSpecifications;
import ru.practicum.hits.specifications.model.ViewStatsRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class EndpointHitServiceImpl implements EndpointHitService {

    private final EndpointHitMapper hitMapper;
    private final EndpointHitRepository hitRepository;

    @Override
    public void addHit(EndpointHitDto endpointHitDto) {
        EndpointHit hit = hitMapper.convertToEntity(endpointHitDto);
        hitRepository.save(hit);
        log.info("Сохранено обращение к эндпоинту {}", hit);
    }

    @Override
    public List<ViewStatsDto> getStats(ViewStatsRequest request) {
        List<EndpointHit> hits = hitRepository.findAll(new ViewStatsSpecifications(request));
        if (request.isUnique()) {
            hits = hits.stream()
                    .filter(distinctByKey(EndpointHit::getId))
                    .collect(Collectors.toList());
        }
        List<ViewStatsDto> viewsDtos = hitMapper.convertToDto(hits);
        for (ViewStatsDto views : viewsDtos) {
            views.setHits(hitRepository.countByUri(views.getUri()));
        }
        log.info("Получена статистика {}", viewsDtos);
        return viewsDtos;
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
